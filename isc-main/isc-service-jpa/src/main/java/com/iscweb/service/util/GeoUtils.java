package com.iscweb.service.util;

import com.google.common.collect.Lists;
import com.iscweb.common.model.dto.entity.core.GeoPointDto;
import com.iscweb.common.model.dto.entity.core.GeoPolygonDto;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;

import java.util.List;

public final class GeoUtils {

    public static Point convert(GeoPointDto pointDto) {
        Point point = null;
        if (pointDto != null) {
            point = new GeometryFactory(new PrecisionModel(), 4326)
                    .createPoint(new Coordinate(pointDto.getX(), pointDto.getY()));
        }

        return point;
    }

    public static GeoPointDto convert(Point point) {
        return point != null ? new GeoPointDto(point.getX(), point.getY()) : null;
    }

    public static GeoPolygonDto convert(Polygon polygon) {
        GeoPolygonDto polygonDto = null;
        if (polygon != null && polygon.getNumPoints() > 0) {
            List<GeoPointDto> points = Lists.newArrayList();
            for (Coordinate point : polygon.getCoordinates()) {
                points.add(new GeoPointDto(point.getX(), point.getY()));
            }

            polygonDto = new GeoPolygonDto(points);
        }

        return polygonDto;
    }

    public static Polygon convert(GeoPolygonDto polygon) {
        Polygon rez = null;
        if (polygon != null) {
            Coordinate[] coordinates = polygon.getPoints()
                    .stream()
                    .map(point -> convert(point).getCoordinate())
                    .toArray(Coordinate[]::new);

            rez = new GeometryFactory(new PrecisionModel(), 4326)
                    .createPolygon(coordinates);
        }

        return rez;
    }
}
