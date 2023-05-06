export default function TablePostAndComment(props) {
    return(
        <table className="table mt-3">
                <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col"></th>
                        <th scope="col"></th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody id="tbody">
                    {props.comments.map((comment) => (
                            <tr key={comment.id}>
                                <th scope="row">{comment.user}</th>
                                <td>{comment.text}</td>
                            </tr>
                    ))}
                </tbody>
        </table>
    );
}