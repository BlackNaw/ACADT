/**
 * Created by Luciano on 23/02/2017.
 */
FRUTA.namespace('FRUTA.Validar');
(function (g) {
    'use strict';
    FRUTA.Validar = {


         colorvalido: 'rgb(139, 238, 251)',
        cadenaSinespacio: function (parametros) {
            var expregular = new RegExp("^([A-Za-zñÑáéíóúÁÉÍÓÚ\\s]{" + parametros.valorMinimo + "," + parametros.valorMaximo + "})$");
            var mensajeError = "Introduce cadena sin espacio y una longitud entre " + parametros.valorMinimo + " y " + parametros.valorMaximo + " caracteres";

            FRUTA.Validar.validarNodo(parametros.nodo, parametros.nodoEnviar, parametros.nodoError, expregular, mensajeError);
        },

        validarNodo: function (nodo, nodoEnviar, nodoError, expregular, mensajeError) {
            //alert(mensajeError);
            var colorError = 'red';
            var minodo = document.getElementById(nodo);
            var elemento = minodo.value;
            var errores = document.getElementById(nodoError);
            var boton = document.getElementById(nodoEnviar);
            if (!expregular.test(elemento)) {
                boton.style.display = "none";
                minodo.style.background = colorError;
                errores.innerHTML = mensajeError;
                errores.style.display = "";
                return false;
            }
            minodo.style.background = this.colorvalido;
            errores.style.display = "none";
        }
    };
})(window);

FRUTA.Validar.cadenaConespacio = function (parametros) {
    var re = new RegExp("^([A-Za-zñÑáéíóúÁÉÍÓÚ\\s]{" + parametros.valorMinimo + "," + parametros.valorMaximo + "})$");
    var mensajeError = "Introduce cadena con una longitud entre " + parametros.valorMinimo + " y "
        + parametros.valorMaximo + " caracteres y sin caracteres especiales.";

    FRUTA.Validar.validarNodo(parametros.nodo, parametros.nodoEnviar, parametros.nodoError, re, mensajeError);
};

FRUTA.Validar.cadenaNumeroConespacio = function (parametros) {
    var re = new RegExp("^([A-Za-z0-9\\s]{" + parametros.valorMinimo + "," + parametros.valorMaximo + "})$");
    var mensajeError = "Introduce cadena con una longitud entre " + parametros.valorMinimo + " y "
        + parametros.valorMaximo + " caracteres y sin caracteres especiales.";

    FRUTA.Validar.validarNodo(parametros.nodo, parametros.nodoEnviar, parametros.nodoError, re, mensajeError);
};

FRUTA.Validar.cadenaNumeroSinespacio = function (parametros) {
    var re = new RegExp("^([A-Za-z0-9]{" + parametros.valorMinimo + "," + parametros.valorMaximo + "})$");
    var mensajeError = "Introduce cadena con una longitud entre " + parametros.valorMinimo + " y "
        + parametros.valorMaximo + " caracteres y sin caracteres especiales.";

    FRUTA.Validar.validarNodo(parametros.nodo, parametros.nodoEnviar, parametros.nodoError, re, mensajeError);
};
