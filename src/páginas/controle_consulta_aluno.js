import React, {useState} from "react";
import logo from "./imagens/logo.png"

function Controleconsultasaluno () {
		return (

		<div>

			<img src={logo}/>

			<div className="cadastrar_consulta_avaliadorfisico">

				<h1>Confirmação de Consultas</h1>

					<p>

						Renan Victor Dias Costa <br/>

						22/02/2024 <br/>

						Consulta realizada

						<br/> <br/>

					</p>

					<p>

						Ciro Dourado <br/>

						23/02/2024 <br/>

						Aluno não compareceu

						<br/> <br/>

					</p>

					<p>

						Renan Victor Dias Costa <br/>

						26/02/2024 <br/>

						<button>Cancelar consulta</button>

						<br/> <br/>

					</p>

					<p>

						Renan Victor Dias Costa <br/>

						27/02/2024 <br/>

						<button>Cancelar consulta</button>

						<br/> <br/>

					</p>

			</div>

		</div>
		);
}

export default Controleconsultasaluno;