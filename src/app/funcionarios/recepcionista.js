import React, { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import LocalStorageService from "../service/localstorageService";
import { AuthContext } from '../main/provetorAutenticacao'
import Card from "../components/card";
import "../decoração/login.css"
import logo from '../imagens/1.png'
import logo2 from '../imagens/3.png'
const RecepcaoPage = () => {
    const context = useContext(AuthContext);

    const navigate = useNavigate();

    const goToCadastroAlunoPage = () => {
        navigate("/cadastro-aluno");
    }
    const goToBuscarAlunoPage = () => {
        navigate("/buscar-aluno");
    }
    const goToBuscarAgendamentoPage = () => {
        navigate("/buscar-agendamento");
    }
    const goToSair = () => {
        context.encerrarSessao();
        navigate("/");
    }
    const usuarioLogado = LocalStorageService.obterItem('_usuario_logado');

    return (
        <Card title={`Bem Vindo ${usuarioLogado.nome} ao Menu Inicial`}>
            <div className='' >
                <div className='text-center' style={{ margin: '5%' }}>
                    <h4>Clique em um dos botão abaixo para comecar a acessar o sistema:</h4> <br />
                </div>

                <div className='row' style={{ margin: '2%' }} >

                    <div className='col'>
                        <button style={{ width: '100%' }}
                            onClick={goToCadastroAlunoPage}
                            className="btn btn-success">
                            <i className="pi pi-plus">Cadastrar Aluno</i>
                        </button>
                    </div>
                    <div className='col'>
                        <button style={{ width: '100%' }}
                            onClick={goToBuscarAlunoPage}
                            className="btn btn-success">
                            <i className="pi pi-search">Buscar Aluno</i>
                        </button>
                    </div>
                    <div className='col'>
                        <button style={{ width: '100%' }}
                            onClick={goToBuscarAgendamentoPage}
                            className="btn btn-success">
                            <i className="pi pi-search">Buscar Agendamento</i>
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
export default RecepcaoPage;

