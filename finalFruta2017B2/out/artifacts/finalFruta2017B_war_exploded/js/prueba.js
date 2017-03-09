/**
 * Created by Juangra on 17/02/2017.
 */
var ajax  = FRUTA.Ajax;
var url = "holamundo.txt";
var cargador;

var contenido = function () {
    alert(cargador.req.responseText);
};

var boton = document.getElementById("publico_entrar").addEventListener("click",function () {
    cargador = new ajax.CargadorContenidos(url, contenido);
});