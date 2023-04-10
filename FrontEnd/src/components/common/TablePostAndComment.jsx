export default function TablePostAndComment(props) {
    const filteredads=props.comments.filter(ads=>{
        return ads.text.includes(props.value);
    })
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
                    {filteredads.map((comment) => (
                            <tr key={comment.id}>
                                {console.log(comment.user)}
                                <th scope="row">{comment.user}</th>
                                <td>{comment.text}</td>
                            </tr>
                    ))}
                </tbody>
        </table>
    );
}