import React, {useState} from "react";
import logo from "./imagens/logo.png"

const Agendamentonutricionista = () => {

	return (

		<div>

			<img src={logo}/>

			<div className="agendar_nutricionista">

				<form action="">

					<h1>Agendar Consulta com Nutricionista</h1>

					<div>

						<input type="text" placeholder='cpf...' required/>

					</div>

					<br/>

					<div>

						<input type="number" placeholder='horas...' required/> <br/>
						
						<input type="number" placeholder="minutos..." required/>

					</div>

					<br/>

					<div>

						<input type="number" placeholder='dia...' required/> <br/>

						<input type="number" placeholder='mês...' required/> <br/>

						<input type="number" placeholder="ano..." required/> <br/>

					</div>

					<br></br>

					<button type="submit">Marcar</button>


				</form>

			</div>

		</div>
		);
}

export default Agendamentonutricionista;
