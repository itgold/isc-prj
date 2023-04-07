export function prettifyName(name: string) {
    if (!name) {
        return name;
    }

    const prettyEntityType = name.replaceAll('_', ' ');
    return prettyEntityType.charAt(0).toUpperCase() + prettyEntityType.substr(1).toLowerCase();
}
