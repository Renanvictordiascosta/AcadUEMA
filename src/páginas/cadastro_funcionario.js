import React, {useState} from "react";
import logo from "./imagens/logo.png"

const Cadastrofuncionario = () => {

	return (

		<div>

			<img src={logo}/>

			<div className="cadastro_funcionario">

				<form action="">

					<h1>Cadastro de Funcionários</h1>

					<div>

						<input type="text" placeholder='nome...' required/>

					</div>

					<br/>

					<div>

						<input type="text" placeholder='cpf...' required/>

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

					<br/>

					<div>

						<input type="password" placeholder='senha...' required/>

					</div>

					<br/>

					<div>

						<input type="text" placeholder='função...' required/>

					</div>

					<br/>

					<br></br>

					<button type="submit">Cadastrar</button>


				</form>

			</div>

		</div>
		);
}

export default Cadastrofuncionario;
