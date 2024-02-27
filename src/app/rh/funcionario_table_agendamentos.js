import React from 'react';

const FuncionarioAgendamentoTable = ({ funcionarios ,agendamentoAction}) => {
    return (
        <table className="table table-hover">
            <thead>
                <tr>
                    <th scope="col">Nome</th>
                    <th scope="col">CPF</th>
                    <th scope="col">Gênero</th>
                    <th scope="col">Tipo</th>
                    <th scope="col">Ações</th>
                </tr>
            </thead>
            <tbody>
                {funcionarios.map((funcionario) => (
                    <tr key={funcionario.id}>
                        <td>{funcionario.nome}</td>
                        <td>{funcionario.cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4')}</td>
                        <td>{funcionario.genero}</td>
                        <td>{funcionario.tipo}</td>
                        <td>
                          
                            <button
                                type="button"
                                title="Agendamento"
                                className="btn btn-warning"
                                onClick={() => agendamentoAction(funcionario.id)}
                            >
                                <i className="pi pi-book"></i>
                            </button>
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
};

export default FuncionarioAgendamentoTable;