const usuario =
JSON.parse(localStorage.getItem("usuario"));

if(!usuario || !usuario.id){

    alert("Usuário não logado");
}

const usuarioId = usuario.id;

const form =
document.getElementById("formConsumo");

const lista =
document.getElementById("listaConsumos");

const formCard =
document.getElementById("cardFormulario");

const btnAbrir =
document.getElementById("btnAbrirFormulario");

const btnFechar =
document.getElementById("btnFecharFormulario");

// ==========================
// ABRIR FORM
// ==========================
btnAbrir.addEventListener("click", () => {

    abrirFormulario();

});

// ==========================
// FECHAR FORM
// ==========================
btnFechar.addEventListener("click", () => {

    fecharFormulario();

});

function abrirFormulario(){

    formCard.classList.add("active");

    window.scrollTo({
        top:0,
        behavior:"smooth"
    });

}

function fecharFormulario(){

    formCard.classList.remove("active");

    form.reset();

    document.getElementById(
        "consumoId"
    ).value = "";

    document.getElementById(
        "tituloFormulario"
    ).innerText = "Novo Consumo";

}

// ==========================
// LISTAR
// ==========================
async function carregarConsumos(){

    const response =
    await fetch(
        `http://localhost:8080/consumo/usuario/${usuarioId}`
    );

    const dados =
    await response.json();

    renderizarConsumos(dados);

}

// ==========================
// RENDER
// ==========================
function renderizarConsumos(dados){

    lista.innerHTML = "";

    if(dados.length === 0){

        lista.innerHTML = `
        <div class="consumo-card">
            Nenhum consumo registrado.
        </div>
        `;

        return;
    }

    dados.reverse().forEach(item => {

        lista.innerHTML += `

        <div class="consumo-card">

            <div class="consumo-info">

                <h5>
                    ${Number(item.consumoKwh).toFixed(2)} kWh
                </h5>

                <p>
                    ${formatarData(item.dataRegistro)}
                </p>

                <div class="badge-consumo">

                    Registro #${item.id}

                </div>

            </div>

            <div class="consumo-actions">

                <button
                class="btn-action btn-edit"
                onclick="editarConsumo(${item.id})">

                Editar

                </button>

                <button
                class="btn-action btn-delete"
                onclick="deletarConsumo(${item.id})">

                Excluir

                </button>

            </div>

        </div>

        `;

    });

}

// ==========================
// FORMATAR DATA
// ==========================
function formatarData(data){

    if(!data) return "Sem data";

    return new Date(data)
    .toLocaleString("pt-BR");

}

// ==========================
// SALVAR
// ==========================
form.addEventListener(
"submit",
async (e) => {

e.preventDefault();

const id =
document.getElementById(
"consumoId"
).value;

const payload = {

usuario:{
id:usuarioId
},

consumoKwh:
parseFloat(
document.getElementById(
"consumoKwh"
).value
),

dataRegistro:
document.getElementById(
"dataRegistro"
).value || null

};

let url =
"http://localhost:8080/consumo/add";

let method = "POST";

if(id){

url =
`http://localhost:8080/consumo/${id}`;

method = "PUT";

}

const response =
await fetch(url,{

method:method,

headers:{
"Content-Type":"application/json"
},

body:JSON.stringify(payload)

});

if(response.ok){

fecharFormulario();

carregarConsumos();

}

});

// ==========================
// EDITAR
// ==========================
async function editarConsumo(id){

const response =
await fetch(
`http://localhost:8080/consumo/${id}`
);

const item =
await response.json();

abrirFormulario();

document.getElementById(
"tituloFormulario"
).innerText =
"Editar Consumo";

document.getElementById(
"consumoId"
).value =
item.id;

document.getElementById(
"consumoKwh"
).value =
item.consumoKwh;

if(item.dataRegistro){

document.getElementById(
"dataRegistro"
).value =
item.dataRegistro.substring(0,16);

}

}

// ==========================
// DELETE
// ==========================
async function deletarConsumo(id){

const confirmar =
confirm("Deseja excluir esse consumo?");

if(!confirmar) return;

const response =
await fetch(
`http://localhost:8080/consumo/${id}`,
{
method:"DELETE"
}
);

if(response.ok){

carregarConsumos();

}

}

// ==========================
carregarConsumos();