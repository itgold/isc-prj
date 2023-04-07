export function dateTimeRangeToString(startDate: Date | null, endDate: Date | null): string {
    return `${startDate?.toISOString()} .. ${endDate?.toISOString()}`;
}

export function startDateTimeFromString(dateTimeRange: string): Date | null {
    if (dateTimeRange) {
        const range = dateTimeRange.split('..');
        if (range.length === 2) {
            return range[0].trim() === 'null' ? null : new Date(range[0].trim());
        }
    }

    return null;
}

export function endDateTimeFromString(dateTimeRange: string): Date | null {
    if (dateTimeRange) {
        const range = dateTimeRange.split('..');
        if (range.length === 2) {
            return range[1].trim() === 'null' ? null : new Date(range[1].trim());
        }
    }

    return null;
}
