import React, {useState} from "react";
import logo from "./imagens/logo.png";
import "./decoração/login.css";

function Login() {

  const [funcionarios, setFuncionarios] = useState({

    cpf: "",
    senha: "",
 
  });

  const [logado, setLogado] = useState("");

  const [erro, setErro] = useState("");

  const deferirInput = (evento) => {

    const { name, value } = evento.target;

    setFuncionarios({ ...funcionarios, [name]: value });
  
  };

  const clickarLogin = () => {

    if (funcionarios.cpf === "6500462900" && funcionarios.senha === "555.555.555.du"){

		console.log("Olá Mundo!")   	

    }

  }

  return (

    <div>

	  <img src={logo}/>

      <h1>Login</h1>

      <form onSubmit={clickarLogin}>

        <div>

          <input
            type="text"
            name="cpf"
            placeholder="cpf..."
            value={funcionarios.cpf}
            onChange={deferirInput}
            required
          />

        </div> <br/>

        <div>

          <input
            type="password"
            name="senha"
            placeholder="senha..."
            value={funcionarios.senha}
            onChange={deferirInput}
            required
          />

        </div>

        <br/> <br/>

        <button type="submit">Logar</button>   

      </form>

    </div>
  );
}

export default Login;
