var FRUTA = FRUTA || {};

FRUTA.namespace = function(ns_string) {
    var parts = ns_string.split('.'), parent = FRUTA, i;

    if (parts[0] === "FRUTA") {
        parts = parts.slice(1); // Crearcuenta

    }

    for (i = 0; i < parts.length; i += 1) {

        if (typeof parent[parts[i]] === "undefined") { // FRUTA['Crearcuenta']
            // === "undefined"
            parent[parts[i]] = {}; // FRUTA['Crearcuenta'] = {}
        }
        parent = parent[parts[i]];
    }

    return parent;

};