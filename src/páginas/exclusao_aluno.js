import React, {useState} from "react";
import logo from "./imagens/logo.png"

function Exclusaoaluno () {
	const [alunos, setAlunos] = useState([

		{id:1, nome: "Renan"},

		{id:2, nome: "Cícero"}

		]);

	const excluirAluno = (id) => {

		setAlunos(alunos.filter(aluno => aluno.id !== id));

	};


	return (

		<div>

			<img src={logo}/>

			<div className="exclusao_aluno">

				<h1>Exclusão de Conta - Alunos</h1>

				{alunos.map(aluno => (

					<p key = {aluno.id}>

						{aluno.nome}

						<button onClick = {

							() => excluirAluno(aluno.id)

						}>Deletar</button>

						<br/> <br/>

						</p>

					))}

			</div>

		</div>
		);
}

export default Exclusaoaluno;
