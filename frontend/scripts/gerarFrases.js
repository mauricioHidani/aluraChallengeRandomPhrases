import postFrases from "./postFrases.js";

const btnGerarFrases = document.querySelector('#btn-gerar-frases');
const inputTitle = document.querySelector('#input-title');
const inputLimit = document.querySelector('#input-limite');
const content = document.getElementById('content');

function gerarFrases() {
  const title = inputTitle.value;
  const limit = inputLimit.value;

  postFrases(`/series?title=${title}&limit=${limit}`)
    .then(data => {
      let items = "";
      console.log(data);

      data.forEach(elem => {
          items += `<li>
            <p><b>Citada por ${elem.personagem}:</b> "${elem.frase}"</p>
          </li>`;
      });
      content.innerHTML = `
        <img src="${data[0].poster}" alt="${data[0].titulo}" />
        <div>
          <h2>${data[0].titulo}</h2>
          <ul class="lista-frases">
            ${items}
          </ul>
        </div>
      `;
    })
    .catch(error => content.innerHTML = `erro: ${error}`);
}

btnGerarFrases.addEventListener('click', gerarFrases);