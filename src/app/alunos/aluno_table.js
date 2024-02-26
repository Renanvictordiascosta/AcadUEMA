import React from 'react';

const AlunoTable = ({ alunos, editAction, deleteAction , agendamentoAction}) => {
    return (
        <table className="table table-hover">
            <thead>
                <tr>
                    <th scope="col">Nome</th>
                    {/* <th scope="col">Email</th> */}
                    <th scope="col">CPF</th>
                    <th scope="col">Telefone</th>
                    <th scope="col">Gênero</th>
                    <th scope="col">Tipo</th>
                    <th scope="col">Ações</th>
                </tr>
            </thead>
            <tbody>
                {alunos.map((aluno) => (
                    <tr key={aluno.id}>
                        <td>{aluno.nome}</td>
                        {/* <td>{aluno.email}</td> */}
                        <td>{aluno.cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4')}</td>
                        <td>{aluno.telefone.replace(/(\d{2})(\d{5})(\d{4})/, '($1)$2-$3')}</td>
                        <td>{aluno.genero}</td>
                        <td>{aluno.tipo}</td>
                        <td>
                            <button
                                type="button"
                                title="Editar"
                                className="btn btn-primary"
                                onClick={() => editAction(aluno.id)}
                            >
                                <i className="pi pi-pencil"></i>
                            </button>
                            <button
                                type="button"
                                title="Agendamento"
                                className="btn btn-warning"
                                onClick={() => agendamentoAction(aluno.id)}
                            >
                                <i className="pi pi-book"></i>
                            </button>
                            <button
                                type="button"
                                title="Excluir"
                                className="btn btn-danger"
                                onClick={() => deleteAction(aluno)}
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

export default AlunoTable;