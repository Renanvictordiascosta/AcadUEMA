import React, {useState} from "react";
import logo from "./imagens/logo.png"

function Exclusaofuncionario () {

	const [funcionarios, setFuncionarios] = useState([

		{id:1, nome: "Reinaldo"},

		{id:2, nome: "Jacob"}

		]);

	const excluirFuncionario = (id) => {

		setFuncionarios(funcionarios.filter(funcionario => funcionario.id !== id));

	};

	return (

		<div>

			<img src={logo}/>

			<div className="exclusao_funcionario">

				<h1>Exclusão de Conta - Funcionários</h1>

				{funcionarios.map(funcionario=>(

					<p key={funcionario.id}>

						{funcionario.nome} 

						<button onClick={

							() => excluirFuncionario(funcionario.id)
						
						}> Deletar </button>

						<br/> <br/>

					</p>

				))}

			</div>

		</div>
		);
}

export default Exclusaofuncionario;
