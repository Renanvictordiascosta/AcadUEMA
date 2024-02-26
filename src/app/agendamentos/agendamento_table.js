import React from 'react';
import Moment from 'moment';
const AgendamentoTable = ({ agendamentos, deleteAction, alterarStatus}) => {
    let formattedTime = '';
    return (
        <table className="table table-hover">
            <thead>
                <tr>
                    <th scope="col">Horario</th>
                    <th scope="col">Dia</th>
                    <th scope="col">Mês</th>
                    <th scope="col">Ano</th>
                    <th scope="col">Aluno</th>
                    <th scope="col">Funcionario</th>
                    <th scope="col">Tipo de Funcionario</th>
                    <th scope="col">Status</th>
                    <th scope="col">Ações</th>
                </tr>
            </thead>
            <tbody>
                {agendamentos.map((agendamento) => (
                    <tr key={agendamento.id}>
                        <td>{ formattedTime = Moment( new Date(0, 0, 0, ...agendamento.horario)).format('HH:mm:ss')}</td>
                        <td>{agendamento.dia}</td>
                        <td>{agendamento.mes}</td>
                        <td>{agendamento.ano}</td>
                        <td>{agendamento.alunos.nome}</td>
                        <td>{agendamento.funcionario.nome}</td>
                        <td>{agendamento.funcionario.tipo}</td>
                        <td>{agendamento.status}</td>
                        <td>

                        <button className="btn btn-success" title="Efetivar"
                            disabled={ agendamento.status !== 'PENDENTE' }
                            onClick={e => alterarStatus(agendamento, 'EFETIVADO')} 
                            type="button">
                            <i className="pi pi-check"></i>
                    </button>
                    <button className="btn btn-warning"  title="Cancelar"
                            disabled={ agendamento.status !== 'PENDENTE' }
                            onClick={e => alterarStatus(agendamento, 'CANCELADO')} 
                            type="button">
                            <i className="pi pi-times"></i>
                    </button>
                            {/* <button

                            
                                type="button"
                                title="Editar"
                                className="btn btn-primary"
                                onClick={() => editAction(agendamento.id)}
                            >
                                <i className="pi pi-pencil"></i>
                            </button>    */}
                          
                            <button
                                type="button"
                                title="Excluir"
                                className="btn btn-danger"
                                onClick={() => deleteAction(agendamento)}
                            >
                                <i className="pi pi-trash"></i>
                            </button>
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
};

export default AgendamentoTable;