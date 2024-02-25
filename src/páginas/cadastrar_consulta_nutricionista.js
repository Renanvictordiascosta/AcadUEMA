import React, {useState} from "react";
import logo from "./imagens/logo.png"

function Confirmarconsultanutricionista () {
		return (

		<div>

			<img src={logo}/>

			<div className="cadastrar_consulta_avaliadorfisico">

				<h1>Validar Consultas - Nutricionista</h1>

					<p>

						Renan Victor Dias Costa <br/>

						27/02/2024 <br/>

						<button>Confirmar ocorrência</button>

						<button>Informar ausência</button>

						<br/> <br/>

					</p>

			</div>

		</div>
		);
}

export default Confirmarconsultanutricionista;
