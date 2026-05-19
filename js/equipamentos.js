const usuario =
JSON.parse(localStorage.getItem("usuario"));

const usuarioId = usuario.id;

const lista =
document.getElementById("listaEquipamentos");

const selectEquip =
document.getElementById("equipamento");

const horasInput =
document.getElementById("horasPorDia");

const consumoPreview =
document.getElementById("consumoEsperado");

const formCard =
document.getElementById("cardFormulario");

const btnAbrir =
document.getElementById("btnAbrirFormulario");

const btnFechar =
document.getElementById("btnFecharFormulario");

const form =
document.getElementById("formEquipUser");

let equipamentosCache = [];

// ==========================
// ABRIR FORM
// ==========================
btnAbrir.addEventListener("click", async () => {

    form.reset();

    formCard.classList.add("active");

    document.getElementById(
        "tituloFormulario"
    ).innerText =
    "Novo equipamento";

    document.getElementById(
        "equipUserId"
    ).value = "";

    consumoPreview.innerText = "0.00";

    await carregarEquipamentosBase();

    formCard.scrollIntoView({
        behavior:"smooth",
        block:"start"
    });

});

// ==========================
// FECHAR FORM
// ==========================
btnFechar.addEventListener("click", () => {

    formCard.classList.remove("active");

    form.reset();

    consumoPreview.innerText = "0.00";

});

// ==========================
// BASE
// ==========================
async function carregarEquipamentosBase(){

    const response =
    await fetch(
        "http://localhost:8080/equipamentos/listar"
    );

    equipamentosCache =
    await response.json();

    selectEquip.innerHTML =
    `<option value="">
    Selecione um equipamento
    </option>`;

    equipamentosCache.forEach(eq => {

        selectEquip.innerHTML += `
        <option value="${eq.id}">
            ${eq.nome}
        </option>
        `;

    });

}

// ==========================
// CONSUMO
// ==========================
function atualizarConsumo(){

    const equipamentoId =
    Number(selectEquip.value);

    const horas =
    Number(horasInput.value);

    if(!equipamentoId || !horas){

        consumoPreview.innerText = "0.00";

        return;
    }

    const equipamento =
    equipamentosCache.find(
        e => Number(e.id) === equipamentoId
    );

    if(!equipamento){

        consumoPreview.innerText = "0.00";

        return;
    }

    const total =
    Number(equipamento.consumoPorHora)
    * horas;

    consumoPreview.innerText =
    total.toFixed(2);

}

selectEquip.addEventListener(
    "change",
    atualizarConsumo
);

horasInput.addEventListener(
    "input",
    atualizarConsumo
);

// ==========================
// LISTAR
// ==========================
async function carregarEquipamentosUsuario(){

    const response =
    await fetch(
        `http://localhost:8080/equipamentousuario/listar/${usuarioId}`
    );

    const dados =
    await response.json();

    renderizarEquipamentos(dados);

}

// ==========================
// RENDER
// ==========================
function renderizarEquipamentos(dados){

    lista.innerHTML = "";

    dados.forEach(item => {

        lista.innerHTML += `

        <div class="equip-card">

            <div class="equip-info">

                <h5>
                    ${item.nomeIdentificacao}
                </h5>

                <p>
                    ${item.equipamento.nome}
                </p>

                <div class="badge-consumo">

                    ${Number(item.consumoEsperado).toFixed(2)}
                    kWh/dia

                </div>

            </div>

            <div class="equip-actions">

                <button
                class="btn-action btn-edit"
                onclick="editarEquipamento(${item.id})">

                Editar

                </button>

                <button
                class="btn-action btn-delete"
                onclick="deletarEquipamento(${item.id})">

                Excluir

                </button>

            </div>

        </div>

        `;

    });

}

// ==========================
// EDITAR
// ==========================
async function editarEquipamento(id){

    await carregarEquipamentosBase();

    const response =
    await fetch(
        `http://localhost:8080/equipamentousuario/${id}`
    );

    const item =
    await response.json();

    formCard.classList.add("active");

    document.getElementById(
        "tituloFormulario"
    ).innerText =
    "Editar equipamento";

    document.getElementById(
        "equipUserId"
    ).value =
    item.id;

    document.getElementById(
        "nomeIdentificacao"
    ).value =
    item.nomeIdentificacao;

    document.getElementById(
        "horasPorDia"
    ).value =
    item.horasPorDia;

    selectEquip.value =
    String(item.equipamento.id);

    atualizarConsumo();

    formCard.scrollIntoView({
        behavior:"smooth",
        block:"start"
    });

}

// ==========================
// DELETE
// ==========================
async function deletarEquipamento(id){

    await fetch(
        `http://localhost:8080/equipamentousuario/${id}`,
        {
            method:"DELETE"
        }
    );

    carregarEquipamentosUsuario();

}

// ==========================
// SALVAR
// ==========================
form.addEventListener("submit", async (e) => {

    e.preventDefault();

    const id =
    document.getElementById(
        "equipUserId"
    ).value;

    const payload = {

        usuario:{
            id:usuarioId
        },

        equipamento:{
            id:Number(selectEquip.value)
        },

        nomeIdentificacao:
        document.getElementById(
            "nomeIdentificacao"
        ).value,

        horasPorDia:
        Number(
            document.getElementById(
                "horasPorDia"
            ).value
        )

    };

    let url =
    "http://localhost:8080/equipamentousuario/add";

    let method = "POST";

    if(id){

        url =
        `http://localhost:8080/equipamentousuario/${id}`;

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

        formCard.classList.remove("active");

        carregarEquipamentosUsuario();

    }

});

// ==========================
// NOVO EQUIPAMENTO
// ==========================
document.getElementById(
"btnSalvarEquipamento"
).addEventListener(
"click",
async () => {

    const payload = {

        nome:
        document.getElementById(
            "novoNome"
        ).value,

        modelo:
        document.getElementById(
            "novoModelo"
        ).value,

        consumoPorHora:
        Number(
            document.getElementById(
                "novoConsumo"
            ).value
        )

    };

    const response =
    await fetch(
        "http://localhost:8080/equipamentos/add",
        {
            method:"POST",

            headers:{
                "Content-Type":"application/json"
            },

            body:JSON.stringify(payload)
        }
    );

    if(response.ok){

        const novoEquip =
        await response.json();

        await carregarEquipamentosBase();

        selectEquip.value =
        String(novoEquip.id);

        atualizarConsumo();

        bootstrap.Modal.getInstance(
            document.getElementById(
                "modalEquipamento"
            )
        ).hide();

    }

});

// ==========================
carregarEquipamentosBase();
carregarEquipamentosUsuario();