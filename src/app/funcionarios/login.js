import React, { useContext } from 'react'
import Card from '../components/card.js'
import FormGroup from '../components/form-group.js'
import { useNavigate } from 'react-router-dom'

import FuncionarioService from '../service/funcionarioService.js'
import AdminService from '../service/adminService.js'
import { mensagemErro } from '../components/toastr.js'
import { AuthContext } from '../main/provetorAutenticacao.js'
import logo from '../imagens/1.png'
import logo2 from '../imagens/3.png'
import '../decoração/login.css';

function Login() {

    const [cpf, setCpf] = React.useState('');
    const [senha, setSenha] = React.useState('');
    const navigate = useNavigate();
    const context = useContext(AuthContext);

    const service = new FuncionarioService();
    const admin_service = new AdminService();

    const entrar_admin = () => {
        admin_service.autenticar({
            cpf: cpf,
            senha: senha
        }).then(response => {
            context.iniciarSessao(response.data)
            navigate("/admin");
        }).catch(erro => {
            mensagemErro(erro.response.data)
        })
    }

    const entrar = () => {
        service.autenticar({
            cpf: cpf,
            senha: senha
        }).then(response => {
            context.iniciarSessao(response.data)
            if (response.data.tipo == "OUTROS") {
                mensagemErro("O funcionario não tem Permissao para Acessar!")
            }
            if (response.data.tipo == "AVALIADOR") {
                navigate('/avaliador')
            }
            if (response.data.tipo == "NUTRICIONISTA") {
                navigate('/nutricionista')
            }
            if (response.data.tipo == "RECEPCAO") {
                navigate('/recepcao')
            }
        }).catch(erro => {
            mensagemErro(erro.response.data)
        })
    }

    return (

        <div className="row  row-cols-1">
            <div className="col-sm-2 offset-sm-5" >
                <img src={logo} style={{ width: '100%', height: '100%' }} />
            </div>
            <div className="col-md-6 offset-md-3" >

                <div className="bs-docs-section">

                    <Card title="Login">
                        <div className="row">
                            <div className="col-lg-12">
                                <div className="bs-component">
                                    <fieldset>
                                        <FormGroup label="CPF: *" htmlFor="exampleInputCPF">
                                            <input type="CPF"
                                                value={cpf}
                                                onChange={e => setCpf(e.target.value)}
                                                className="form-control"
                                                id="exampleInputCPF" />
                                        </FormGroup>
                                        <FormGroup label="Senha: *" htmlFor="exampleInputPassword1">
                                            <input type="password"
                                                value={senha}
                                                onChange={e => setSenha(e.target.value)}
                                                className="form-control"
                                                id="exampleInputPassword1"
                                                placeholder="Password" />
                                        </FormGroup>
                                        <div className="row">
                                            <div className="col" style={{ margin: '2%' }}>
                                                <button onClick={entrar} className="btn btn-success">
                                                    <i className="pi pi-sign-in"></i>Entrar</button>
                                            </div>
                                            <div className="col" style={{ margin: '2%' }}>
                                                <button onClick={entrar_admin} className="btn btn-primary">
                                                    <i className="pi pi-sign-in"></i>Acesso Administrativo</button>
                                            </div>
                                        </div>
                                    </fieldset>
                                </div>
                            </div>
                        </div>
                    </Card>
                </div>
            </div>
        </div>


    )
}

export default Login
