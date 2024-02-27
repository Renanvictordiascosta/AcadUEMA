import React, { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import LocalStorageService from "../service/localstorageService";
import { AuthContext } from '../main/provetorAutenticacao'
import Card from "../components/card";
import "../decoração/login.css"
const AdminPage = () => {
    const context = useContext(AuthContext);

    const navigate = useNavigate();

    const goToCadastroFuncionarioPage = () => {
        navigate("/cadastro-funcionario");
    }
    const goToBuscarFuncionarioPage = () => {
        navigate("/buscar-funcionario");
    }
    const goToSair = () => {
        context.encerrarSessao();
        navigate("/");
    }
    const usuarioLogado = LocalStorageService.obterItem('_usuario_logado');

    return (
        <Card title={`Bem Vindo ${usuarioLogado.nome} a Seção dos Recursos Humanos `}>
            <div className="container text-center">
                <div className='' style={{ margin: '5%' }}>
                    <h4>Clique em um dos botão abaixo para comecar a acessar o sistema:</h4> <br />
                </div>

                <div className='row' style={{ margin: '2%' }} >

                    <div className='col'>
                        <button style={{ width: '100%' }}
                            onClick={goToCadastroFuncionarioPage}
                            className="btn btn-success">
                            <i className="pi pi-plus">Cadastrar Funcionario</i>
                        </button>
                    </div>
                    <div className='col'>
                        <button style={{ width: '100%' }}
                            onClick={goToBuscarFuncionarioPage}
                            className="btn btn-success">
                            <i className="pi pi-search">Buscar Funcionario</i>
                        </button>
                    </div>
                    <div className='col'>
                        <button style={{ width: '100%' }}
                            onClick={goToSair}
                            className="btn btn-success">
                            <i className="pi pi-sign-in">Sair</i>
                        </button>
                    </div>

                </div>

            </div>
        </Card>

    );
}
export default AdminPage;

