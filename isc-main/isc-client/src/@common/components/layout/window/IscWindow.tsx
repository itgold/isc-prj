import React, { RefObject } from 'react';
import CSS from 'csstype';
import { defaultTheme, WindowTheme } from './IscWindowTheme';

const DEFAULT_MIN_WIDTH = 20;
const DEFAULT_MIN_HEIGHT = 20;
const DEFAULT_EDGE_DETACTION_RANGE = 4;

export interface IscWindowProps {
    transition?: string;
    theme?: WindowTheme;
    titleBar: JSX.Element;
    style: React.CSSProperties;
    contentStyle: React.CSSProperties;
    titleStyle: React.CSSProperties;
    minWidth?: number;
    minHeight?: number;
    edgeDetectionRange?: number;
    initialWidth?: number;
    initialHeight?: number;
    initialTop?: number;
    initialLeft?: number;
    animate?: boolean;
    onMove: (currentFrame: Rect, prevFrame: Rect) => void;
    onResize: (currentFrame: Rect, prevFrame: Rect) => void;
    onTransform: (currentFrame: Rect, prevState?: IscWindowState) => void;
    cursorRemap: (cursor?: CSS.Property.Cursor) => string | undefined;
    boundary: Record<string, unknown>;
    attachedTo?: Window;
}

const disableSelect = {
    userSelect: 'none',
    WebkitUserSelect: 'none',
    msUserSelect: 'none',
    MozUserSelect: 'none',
    OUserSelect: 'none',
};

export interface Rect {
    width: number;
    height: number;
    top: number;
    left: number;
}

export function isRectEqual(rect1: Rect, rect2: Rect): boolean {
    return (
        rect1?.top === rect2?.top &&
        rect1?.left === rect2?.left &&
        rect1?.width === rect2?.width &&
        rect1?.height === rect2?.height
    );
}

interface IscWindowState {
    cursor?: CSS.Property.Cursor;
    transition?: React.CSSProperties;

    top?: number;
    bottom?: number;
    left?: number;
    right?: number;

    height?: number;
    width?: number;
}

export function emptyRect(): DOMRect {
    return {
        height: 100,
        width: 100,
        x: 0,
        y: 0,
        bottom: 0,
        left: 0,
        right: 0,
        top: 0,
    } as DOMRect;
}

export function cumulativeOffset(childElement: HTMLElement): { top: number; left: number } {
    let top = 0;
    let left = 0;
    let element = childElement;
    do {
        top += element.offsetTop || 0;
        left += element.offsetLeft || 0;
        element = element.offsetParent as HTMLElement;
    } while (element);

    return {
        top,
        left,
    };
}

export function getFrameRect(element: HTMLDivElement | null): DOMRect {
    let rect: DOMRect = emptyRect();
    if (element) {
        rect = element.getBoundingClientRect();
        const offset = cumulativeOffset(element);
        rect = {
            top: rect.top - offset.top,
            left: rect.left - offset.left,
            width: rect.width,
            height: rect.height,
        } as DOMRect;
    }

    return rect;
}

function prefixedTransition(transition?: string): React.CSSProperties {
    return transition
        ? {
              transition,
              WebkitTransition: transition,
              msTransition: transition,
              MozTransition: transition,
              OTransition: transition,
          }
        : {};
}

export default class IscWindow extends React.Component<IscWindowProps, IscWindowState> {
    // eslint-disable-next-line react/static-property-placement
    public static defaultProps: Partial<IscWindowProps> = {
        minWidth: DEFAULT_MIN_WIDTH,
        minHeight: DEFAULT_MIN_HEIGHT,
        edgeDetectionRange: DEFAULT_EDGE_DETACTION_RANGE,
        theme: defaultTheme,
        initialWidth: undefined,
        initialHeight: undefined,
        initialTop: undefined,
        initialLeft: undefined,
        animate: true,
        attachedTo: window,
    };

    frameRef: RefObject<HTMLDivElement> = React.createRef();

    titleRef: RefObject<HTMLDivElement> = React.createRef();

    cursorX: number;

    cursorY: number;

    clicked: {
        x: number;
        y: number;
        boundingBox: DOMRect;
        frameTop: number;
        frameLeft: number;
    } | null;

    allowTransition: boolean;

    frameRect: Rect;

    mouseMoveListener: (e: React.MouseEvent<HTMLElement>) => void;

    mouseUpListener: (e: React.MouseEvent<HTMLElement>) => void;

    prevState?: IscWindowState;

    hitEdges?: {
        top: boolean;
        bottom: boolean;
        left: boolean;
        right: boolean;
    };

    constructor(props: IscWindowProps) {
        super(props);

        const { transition, theme } = this.props;
        this.cursorX = 0;
        this.cursorY = 0;
        this.clicked = null;
        this.allowTransition = false;
        this.frameRect = emptyRect();
        this.state = {
            cursor: 'auto',
            transition: prefixedTransition(transition || theme?.transition),
        };

        this.mouseMoveListener = this._onMove.bind(this);
        this.mouseUpListener = this._onUp.bind(this);
    }

    componentDidMount(): void {
        const { initialWidth, initialHeight, initialTop, initialLeft, attachedTo } = this.props;

        const boundingBox = this.getFrameRect();
        this.frameRect.width = initialWidth || boundingBox.width;
        this.frameRect.height = initialHeight || boundingBox.height;
        this.frameRect.top = initialTop || this.frameRef.current?.offsetTop || 0;
        this.frameRect.left = initialLeft || this.frameRef.current?.offsetLeft || 0;

        attachedTo?.addEventListener('mousemove', (this.mouseMoveListener as unknown) as EventListener);
        attachedTo?.addEventListener('mouseup', (this.mouseUpListener as unknown) as EventListener);

        this.forceUpdate();
    }

    UNSAFE_componentWillReceiveProps(nextProps: IscWindowProps): void {
        if (nextProps.transition !== this.props.transition) {
            this.setState({ transition: prefixedTransition(nextProps.transition) });
        }
    }

    componentWillUnmount(): void {
        this.props.attachedTo?.removeEventListener('mousemove', (this.mouseMoveListener as unknown) as EventListener);
        this.props.attachedTo?.removeEventListener('mouseup', (this.mouseUpListener as unknown) as EventListener);
    }

    getTitleRect(): DOMRect {
        return this.titleRef.current?.getBoundingClientRect() || emptyRect();
    }

    getFrameRect(): DOMRect {
        return this.frameRef.current?.getBoundingClientRect() || emptyRect();
    }

    restore(allowTransition = true): void {
        if (this.prevState) {
            this.transform(this.prevState, allowTransition);
        }
    }

    minimize(allowTransition = true): void {
        this.transform({ width: 0, height: 0 }, allowTransition);
    }

    maximize(allowTransition = true): void {
        this.transform(
            { top: 0, left: 0, width: this.props.attachedTo?.innerWidth, height: this.props.attachedTo?.innerHeight },
            allowTransition
        );
    }

    _cursorStatus(e: React.MouseEvent<HTMLElement>): void {
        const boundingBox = this.getFrameRect();
        this.cursorX = e.clientX;
        this.cursorY = e.clientY;

        if (this.clicked) return;

        const hitRange = this.props.edgeDetectionRange || DEFAULT_EDGE_DETACTION_RANGE;
        const hitTop = this.cursorY <= boundingBox.top + hitRange;
        const hitBottom = this.cursorY >= boundingBox.bottom - hitRange;
        const hitLeft = this.cursorX <= boundingBox.left + hitRange;
        const hitRight = this.cursorX >= boundingBox.right - hitRange;

        let cursor = 'auto';

        if (hitTop || hitBottom || hitLeft || hitRight) {
            if ((hitRight && hitBottom) || (hitLeft && hitTop)) {
                cursor = 'nwse-resize';
            } else if ((hitRight && hitTop) || (hitBottom && hitLeft)) {
                cursor = 'nesw-resize';
            } else if (hitRight || hitLeft) {
                cursor = 'ew-resize';
            } else if (hitBottom || hitTop) {
                cursor = 'ns-resize';
            }
            e.stopPropagation();
        } else {
            const titleBounding = this.getTitleRect();
            if (
                this.cursorX > titleBounding.left &&
                this.cursorX < titleBounding.right &&
                this.cursorY > titleBounding.top &&
                this.cursorY < titleBounding.bottom
            ) {
                cursor = 'move';
            }
        }

        this.hitEdges = {
            top: hitTop,
            bottom: hitBottom,
            left: hitLeft,
            right: hitRight,
        };

        if (cursor !== this.state.cursor) {
            this.setState({ cursor });
        }
    }

    _onDown(e: React.MouseEvent<HTMLElement>): void {
        this.allowTransition = false;
        this._cursorStatus(e);

        if (this.frameRef.current) {
            const boundingBox = this.getFrameRect();
            this.clicked = {
                x: e.clientX,
                y: e.clientY,
                boundingBox,
                frameTop: this.frameRef.current.offsetTop,
                frameLeft: this.frameRef.current.offsetLeft,
            };
        }
    }

    _onUp(e: React.MouseEvent<HTMLElement>): void {
        this.clicked = null;
        this._cursorStatus(e);
    }

    _onMove(e: React.MouseEvent<HTMLElement>): void {
        e.stopPropagation();
        e.preventDefault();

        this._cursorStatus(e);
        if (this.clicked !== null) {
            this.forceUpdate();
        }
    }

    transform(state: IscWindowState, allowTransition = true, updateHistory = true): void {
        const boundingBox = this.getFrameRect();

        const top = this.frameRef.current?.offsetTop || 0;
        const left = this.frameRef.current?.offsetLeft || 0;
        const { width } = boundingBox;
        const { height } = boundingBox;

        if (updateHistory) {
            this.prevState = {
                top,
                left,
                width,
                height,
            };
        }

        if (!state) return;

        this.frameRect.top =
            typeof state.top === 'number' ? state.top : state.bottom ? state.bottom - (state.height || height) : top;
        this.frameRect.left =
            typeof state.left === 'number' ? state.left : state.right ? state.right - (state.width || width) : left;
        this.frameRect.width =
            typeof state.width === 'number'
                ? state.width
                : typeof state.right === 'number' && typeof state.left === 'number'
                ? state.right - state.left
                : typeof state.right === 'number'
                ? state.right - (this.frameRect?.left || 0)
                : width;
        this.frameRect.height =
            typeof state.height === 'number'
                ? state.height
                : typeof state.bottom === 'number' && typeof state.top === 'number'
                ? state.top - state.bottom
                : typeof state.bottom === 'number'
                ? state.bottom - (this.frameRect?.top || 0)
                : height;
        this.allowTransition = allowTransition;

        if (this.props.onTransform) {
            setTimeout(this.props.onTransform.bind(this, this.frameRect, this.prevState));
        }
        this.forceUpdate();
    }

    render(): JSX.Element {
        const {
            style,
            contentStyle,
            titleStyle,
            theme,
            minWidth,
            minHeight,
            animate,
            cursorRemap,
            children,
            boundary,
            onMove,
            onResize,
        } = this.props;

        const minWidthProp = minWidth || DEFAULT_MIN_WIDTH;
        const minHeightProp = minHeight || DEFAULT_MIN_HEIGHT;
        const pervFrameRect = { ...this.frameRect };

        if (this.clicked) {
            const hits = this.hitEdges;
            const { boundingBox } = this.clicked;

            if (hits && (hits.top || hits.bottom || hits.left || hits.right)) {
                if (hits.right) this.frameRect.width = Math.max(this.cursorX - boundingBox.left, minWidthProp);
                if (hits.bottom) this.frameRect.height = Math.max(this.cursorY - boundingBox.top, minHeightProp);

                if (hits.left) {
                    const currentWidth = boundingBox.right - this.cursorX;
                    if (currentWidth > minWidthProp) {
                        this.frameRect.width = currentWidth;
                        this.frameRect.left = this.clicked.frameLeft + this.cursorX - this.clicked.x;
                    }
                }

                if (hits.top) {
                    const currentHeight = boundingBox.bottom - this.cursorY;
                    if (currentHeight > minHeightProp) {
                        this.frameRect.height = currentHeight;
                        this.frameRect.top = this.clicked.frameTop + this.cursorY - this.clicked.y;
                    }
                }
            } else if (this.state.cursor === 'move') {
                this.frameRect.top = this.clicked.frameTop + this.cursorY - this.clicked.y;
                this.frameRect.left = this.clicked.frameLeft + this.cursorX - this.clicked.x;
            }
        }

        if (boundary && this.frameRect) {
            const { top, left, width, height } = this.frameRect;
            if (typeof boundary.top === 'number' && top < boundary.top) {
                this.frameRect.top = boundary.top;
            }
            if (typeof boundary.bottom === 'number' && top + height > boundary.bottom) {
                this.frameRect.top = boundary.bottom - height;
                if (typeof boundary.top === 'number' && this.frameRect.top < boundary.top) {
                    this.frameRect.top = boundary.top;
                    this.frameRect.height = boundary.bottom - boundary.top;
                }
            }
            if (typeof boundary.left === 'number' && left < boundary.left) {
                this.frameRect.left = boundary.left;
            }
            if (typeof boundary.right === 'number' && top + height > boundary.right) {
                this.frameRect.left = boundary.right - width;
                if (typeof boundary.left === 'number' && this.frameRect.left < boundary.left) {
                    this.frameRect.left = boundary.left;
                    this.frameRect.width = boundary.right - boundary.left;
                }
            }
        }

        let { cursor } = this.state;
        if (cursorRemap) {
            const res = cursorRemap.call(this, cursor);
            if (res) {
                cursor = res;
            }
        }

        const dnrState = {
            cursor,
            clicked: this.clicked,
            frameRect: this.frameRect,
            allowTransition: this.allowTransition,
        };

        const titleBar = (
            <div
                ref={this.titleRef}
                style={{
                    ...theme?.title,
                    ...titleStyle,
                    cursor,
                }}
            >
                {typeof this.props.titleBar !== 'string'
                    ? React.cloneElement(this.props.titleBar, { dnrState })
                    : this.props.titleBar}
            </div>
        );

        const childrenWithProps = React.Children.map(children, child => {
            return typeof child === 'string' ? child : React.cloneElement(child as React.ReactElement, { dnrState });
        });

        const frameTransition = animate && this.allowTransition ? this.state.transition : {};

        if (onMove && !isRectEqual(this.frameRect, pervFrameRect)) {
            setTimeout(onMove.bind(this, this.frameRect, pervFrameRect));
        }

        if (onResize && !isRectEqual(this.frameRect, pervFrameRect)) {
            setTimeout(onResize.bind(this, this.frameRect, pervFrameRect));
        }

        return (
            <div
                ref={this.frameRef}
                onMouseDownCapture={this._onDown.bind(this)}
                onMouseMoveCapture={e => {
                    if (this.clicked !== null) {
                        e.preventDefault();
                    }
                }}
                style={
                    {
                        ...theme?.frame,
                        ...frameTransition,
                        cursor,
                        ...style,
                        ...this.frameRect,
                        ...(this.clicked ? disableSelect : {}),
                        zIndex: 1000,
                    } as React.CSSProperties
                }
            >
                {titleBar}
                <div
                    style={{
                        position: 'absolute',
                        width: '100%',
                        top: theme?.title?.height || 0,
                        bottom: 0,
                        ...contentStyle,
                    }}
                >
                    {childrenWithProps}
                </div>
            </div>
        );
    }
}
