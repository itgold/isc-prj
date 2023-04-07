import { Filter } from 'app/components/table/IscTableContext';
import { CompositeNode, CompositeNodesMap } from 'app/domain/composite';
import { hasGeoData } from 'app/utils/domain.constants';

export interface ICompositeTreeDataContext {
    filter?: Filter;
}

export interface ICompositeTreeDataProvider {
    context: ICompositeTreeDataContext;
    root: CompositeNode;
}

interface MatchedGroup {
    start: number;
    end: number;
}
interface MatcherResult {
    match: boolean;
    matchedGroups?: MatchedGroup[];
}
type SearchMatcher = (node: CompositeNode, treeContext: ICompositeTreeDataContext) => MatcherResult;
type SearchResultHighlighter = (nodeText: string, matchResult: MatchedGroup[]) => string;

const SimpleMatcher: SearchMatcher = (node: CompositeNode, treeContext: ICompositeTreeDataContext): MatcherResult => {
    const filter = treeContext?.filter?.name;
    if (node?.name && filter) {
        const matchIndex = node.name.toLowerCase().indexOf(filter.toLowerCase());
        const matchByGeo = matchIndex < 0 && filter === '?' && !hasGeoData(node);

        return {
            match: matchIndex >= 0 || matchByGeo,
            matchedGroups: matchIndex >= 0 ? [{ start: matchIndex, end: matchIndex + filter.length }] : undefined,
        };
    }

    return {
        match: true,
    };
};

const HtmlHighligter: SearchResultHighlighter = (nodeText: string, matchResult: MatchedGroup[]): string => {
    let highlighted = '';
    let lastIndex = 0;
    matchResult.forEach((group, idx) => {
        if (idx === 0) {
            highlighted += nodeText.substr(0, group.start);
        }

        highlighted += `<b style='color: red;'>${nodeText.substr(group.start, group.end - group.start)}</b>`;
        lastIndex = group.end;
    });
    highlighted += nodeText.substring(lastIndex);
    return highlighted;
};

function applyContext(
    node: CompositeNode,
    treeContext: ICompositeTreeDataContext,
    matcher: SearchMatcher,
    highlighter: SearchResultHighlighter
): CompositeNode {
    const clone = { ...node };

    const children: CompositeNodesMap = {};
    if (node.children) {
        Object.values(node.children).forEach(child => {
            const childId = child.id;
            if (childId) {
                const matchResult = matcher(child, treeContext);
                if (matchResult.match) {
                    children[childId] = applyContext(child, treeContext, matcher, highlighter);
                    const { name } = children[childId];
                    if (name && matchResult.matchedGroups?.length) {
                        children[childId].name = highlighter(name, matchResult.matchedGroups);
                    }
                } else {
                    const grandChild = applyContext(child, treeContext, matcher, highlighter);
                    const grandChildren = Object.values(grandChild?.children || {});
                    if (grandChildren.length) {
                        children[childId] = grandChild;
                    }
                }
            }
        });
    }
    clone.children = children;

    return clone;
}

export class CompositeTreeDataProvider implements ICompositeTreeDataProvider {
    _context: ICompositeTreeDataContext;

    _root: CompositeNode;

    _filtered: CompositeNode;

    _matcher: SearchMatcher;

    _highlighter: SearchResultHighlighter;

    constructor(
        rootNode?: CompositeNode,
        context?: ICompositeTreeDataContext,
        matcher: SearchMatcher = SimpleMatcher,
        highlighter = HtmlHighligter
    ) {
        this._context = context || {};
        this._root = rootNode || {};
        this._matcher = matcher;
        this._highlighter = highlighter;

        this._filtered = this._root
            ? applyContext(this._root, this._context, this._matcher, this._highlighter)
            : this._root;
    }

    get root(): CompositeNode {
        return this._filtered;
    }

    get context(): ICompositeTreeDataContext {
        return this._context;
    }
}

export { SimpleMatcher, HtmlHighligter };
