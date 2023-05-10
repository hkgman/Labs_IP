export default function TableComment(props) {
    function edit(id,user,e) {
        props.onEdit(id,user);
    }

    function remove(id,user,e) {
        props.onRemove(id,user,e);
    }


    return(
        <table className="table mt-3">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Text</th>
                        <th scope="col"></th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody id="tbody">
                    {props.comments.map((comment) => (
                            <tr key={comment.id}>
                                {console.log(comment.user)}
                                <th scope="row">{comment.user}</th>
                                <td>{comment.text}</td>
                                <td><button type="button" className="btn btn-outline-light text-center mx-2" onClick={(e) => edit(comment.id,comment.userId, e)}><i className="fa-sharp fa-solid fa-pen"></i></button></td>
                                <td><button type="button" className="btn btn-outline-light text-center mx-2" onClick={(e) => remove(comment.id,comment.userId,e)}><i className="fa-sharp fa-solid fa-trash"></i></button></td>
                            </tr>
                    ))}
                </tbody>
        </table>
    );
}