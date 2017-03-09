var FRUTA = FRUTA || {};
FRUTA.namespace = function (ns_string) {
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
FRUTA.generarOpcion = function(lista, valor) {

	var option = document.createElement("option");
	option.text = valor;
	lista.add(option);
};
FRUTA.generarEncabezado = function(etiqueta, destino, titulo, clase, id, evento,funcionRetorno) {
	var nodo = document.createElement(etiqueta);
	if (titulo)
		nodo.innerHTML = titulo;
	if (clase)
		nodo.className = clase;
	if (id !== null)nodo.id = id;
	if (evento)
		nodo.addEventListener(evento, funcionRetorno);
	destino.appendChild(nodo);
	return nodo;
};
FRUTA.generarInput = function(tipo, destino, valor, clase, minimo, maximo,
		evento, funcionRetorno) {
	var input = document.createElement("input");
	input.type = tipo;
	input.value = valor;
	if (tipo == "number") {
		input.min = minimo || 1;
		input.max = maximo || 50;
		input.style.width = "40px";
	}
	if (evento)
		input.addEventListener(evento, funcionRetorno);
	if (clase)
		input.className = clase;
	destino.appendChild(input);
};
FRUTA.generarBoton = function(destino, caption, id) {
	var input = document.createElement("input");
	input.type = "button";
	input.value = caption;
	input.id = id;
	destino.appendChild(input);
}
FRUTA.generarEnlace = function (){
	
};
