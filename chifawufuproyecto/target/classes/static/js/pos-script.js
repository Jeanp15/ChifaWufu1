/* * Script para el Punto de Venta (pos-formulario.html)
 */

// Esta variable 'listaProductos' será llenada por Thymeleaf
// en el archivo HTML antes de que se cargue este script.
// Es la única parte que "conecta" el script con el servidor.
if (typeof listaProductos === 'undefined') {
    const listaProductos = [];
}

document.addEventListener("DOMContentLoaded", function() {
    // Asegurarnos de que el botón existe antes de añadirle el evento
    const botonAgregar = document.getElementById("btn-agregar-producto");
    if (botonAgregar) {
        botonAgregar.addEventListener("click", function() {
            agregarFila();
        });
    }

    // Añadir evento a los botones "Eliminar" que ya existen (para el caso de error)
    document.querySelectorAll('#tabla-detalles .btn-danger').forEach(boton => {
        boton.onclick = function() { eliminarFila(this); };
    });
});

function agregarFila() {
    const tablaBody = document.getElementById("tabla-detalles").getElementsByTagName('tbody')[0];
    const nuevoIndice = tablaBody.rows.length;
    const nuevaFila = tablaBody.insertRow();

    // --- Celda 1: Dropdown de Productos ---
    const celdaProducto = nuevaFila.insertCell(0);
    let selectProducto = document.createElement("select");
    selectProducto.name = `detalles[${nuevoIndice}].idProducto`;
    selectProducto.className = "form-select";
    selectProducto.required = true;

    let optDefecto = document.createElement("option");
    optDefecto.value = "";
    optDefecto.text = "Seleccione un producto...";
    selectProducto.appendChild(optDefecto);

    // Llenamos el <select> con los productos de la variable global
    listaProductos.forEach(prod => {
        let opt = document.createElement("option");
        opt.value = prod.idProducto;
        opt.text = `${prod.nombre} (S/ ${prod.precio.toFixed(2)} - Stock: ${prod.stock})`;
        selectProducto.appendChild(opt);
    });
    celdaProducto.appendChild(selectProducto);

    // --- Celda 2: Input de Cantidad ---
    const celdaCantidad = nuevaFila.insertCell(1);
    let inputCantidad = document.createElement("input");
    inputCantidad.type = "number";
    inputCantidad.name = `detalles[${nuevoIndice}].cantidad`;
    inputCantidad.className = "form-control";
    inputCantidad.placeholder = "Cant.";
    inputCantidad.min = "1";
    inputCantidad.required = true;
    celdaCantidad.appendChild(inputCantidad);

    // --- Celda 3: Botón de Eliminar ---
    const celdaAccion = nuevaFila.insertCell(2);
    let btnEliminar = document.createElement("button");
    btnEliminar.type = "button";
    btnEliminar.className = "btn btn-danger";
    btnEliminar.innerText = "Eliminar";
    btnEliminar.onclick = function() { eliminarFila(this); };
    celdaAccion.appendChild(btnEliminar);
}

function eliminarFila(boton) {
    const fila = boton.parentNode.parentNode;
    const tablaBody = fila.parentNode;
    
    // No dejamos eliminar la última fila
    if (tablaBody.rows.length > 1) {
        tablaBody.removeChild(fila);
    } else {
        alert("No se puede eliminar la última fila.");
    }
}