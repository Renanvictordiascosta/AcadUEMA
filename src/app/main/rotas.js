import React from 'react'
import { AuthConsumer } from '../main/provetorAutenticacao'
import Login from '../funcionarios/login'
import { Route, Routes, BrowserRouter } from 'react-router-dom'
import RecepcaoPage from '../funcionarios/recepcionista'
import ConsultaAluno from '../alunos/consulta_aluno'
import CadastroAluno from '../alunos/cadastro_alunos'
import CadastroAgendamento from '../agendamentos/agendamento_recepcao'
import ConsultarAgendamentos from '../agendamentos/consulta_agendamentos_recepcao'
import TelaAvaliador from '../agendamentos/agendamento_avaliadorfisico'
import TelaNutricionista from '../agendamentos/agendamento_nutricionista'

import  TelaAdmin from '../rh/telaAdmin'
import CadastroFuncionario from '../rh/cadastro_funcionarios'
import ConsultaFuncionario from '../rh/consulta_funcionario'
function Rotas(props) {
    return (
        <BrowserRouter>
             <Routes>
                <Route path="/" element={<Login />} />
                <Route path='/recepcao' element={<RecepcaoPage />} />
                <Route path='/buscar-aluno' element={<ConsultaAluno/>} />
                <Route path='/cadastro-aluno/:id?' element={<CadastroAluno/>}/>
                <Route path='/agendamento-aluno/:id?' element={<CadastroAgendamento/>}/>
                <Route path='/buscar-agendamento' element={<ConsultarAgendamentos/>}/>
                <Route path='/avaliador' element={<TelaAvaliador/>}/>
                <Route path='/nutricionista' element={<TelaNutricionista/>}/>
                <Route path='/admin' element={<TelaAdmin/>}/>
                <Route path='/cadastro-funcionario/:id?' element={<CadastroFuncionario/>}/>
                <Route path='/buscar-funcionario' element={<ConsultaFuncionario/>}/>
               
            </Routes>
        </BrowserRouter>
    )
}
const RotasComponent = () => (
    <AuthConsumer>
        {(context) => (<Rotas isUsuarioAutenticado={context.isAutenticado} />)}
    </AuthConsumer>
)

export default RotasComponent;


