import React, {useState} from "react";
import logo from "./imagens/logo.png"

const Cadastroaluno = () => {

	return (

		<div>

			<img src={logo}/>

			<div className="cadastro_aluno">

				<form action="">

					<h1>Cadastro Aluno</h1>

					<div>

						<input type="text" placeholder='nome...' required/>

					</div>

					<br/>

					<div>

						<input type="text" placeholder='cpf...' required/>

					</div>

					<br/>

					<div>

						<input type="text" placeholder='email...' required/>

					</div>

					<br/>

					<div>

						<input type="text" placeholder='telefone...' required/>

					</div>

					<div>

						<p>Gênero</p>

						<input type="checkbox" name="masculino"/>Masculino

						<input type="checkbox" name="masculino"/>Feminino

						<input type="checkbox" name="masculino"/>Outros

					</div>

					<div>

						<p>Tipo de Matrícula</p>

						<input type="checkbox" name='comunidade'/>COMUNIDADE

						<input type="checkbox" name='uema'/>UEMA

					</div>

					<br/><br/>

					<button type="submit">Cadastrar</button>

				</form>

			</div>

		</div>
		);
}

export default Cadastroaluno;
