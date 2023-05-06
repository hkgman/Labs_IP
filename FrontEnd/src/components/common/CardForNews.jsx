import { useEffect, useState } from 'react';
import ModalEdit from './ModalComment';
import TablePostAndComment from './TablePostAndComment';
export default function CardForNews(props) {
    
    function edit(id) {
        props.onEdit(id);
    }
    const getTokenForHeader = function () {
        return "Bearer " + localStorage.getItem("token");
    }

    const getUser = async function () {
        const requestParams = {
            method: "GET",
            headers: {
                "Authorization": getTokenForHeader(),
            }
        };
        let login = localStorage.getItem("user");
        const requestUrl = host + `/user?login=${login}`;
        const response = await fetch(requestUrl, requestParams);
        const user = await response.json();
        return user;
    }
    function remove(id) {
        props.onRemove(id);
    }
    useEffect(() => {
        getAll();
    }, []);
    
    const [clients, setClients] = useState([]);
    const getAll = async function () {
        const requestParams = {
            method: "GET",
            headers: {
                "Authorization": getTokenForHeader(),
            }
        };
        const requestUrl = "http://localhost:8080/api/1.0/userList";
        const response = await fetch(requestUrl,requestParams);
        const users = await response.json();
        setClients(users);
    }
    const [modalTable, setModalTable] = useState(false);
    const [currEditItem, setCurrEditItem] = useState(0);
    const [text, setText] = useState('');
    function handleEdit(id) {
        console.info("Start edit script");
        setCurrEditItem(id);
        setModalTable(true)
        console.info('End edit script');
    };
    const handleSubmitEdit = async (e, id) => {
        console.info('Start synchronize edit');
        e.preventDefault(); // страница перестает перезагружаться
        const requestUrl = `http://localhost:8080/api/1.0/post/${id}/Comment/${props.userId}?Text=${text}`;
            const requestParams = {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
            };
            await fetch(requestUrl,requestParams);
            setText('');
            setModalTable(false);
    };


    return (
        <div className="container-fluid mt-3">
            {
                props.items.map((item, index) =>
                    <div key={item.id}
                        className="card border-dark mb-3 d-flex flex-row text-black justify-content-between">
                        <div className=''>
                            <img className="col" style={{width: "300px" ,height: "200px" }} src={item.image}/>
                        </div>
                        <div className='d-flex flex-grow-1 flex-column'>
                            <div className='flex-grow-1 mx-2'>
                                {
                                    props.headers.map(header =>
                                        header.name == "image" ? null :
                                            <div className="" key={item.id + header.name}>{item[header.name]}</div>
                                    )
                                }
                            </div>
                            <div className="d-flex flex-row justify-content-end ">
                                <button type="button" className="btn btn-outline-primary text-center mx-2" data-bs-toggle="modal" data-bs-target="#redact" onClick={(e) => edit(item.id, e)}>
                                    <i className="fa-sharp fa-solid fa-pen"></i>
                                </button>
                                <button href="#"
                                    className="btn btn-outline-primary mx-3"
                                    onClick={(e) => remove(item.id, e)}><i className="fa-sharp fa-solid fa-trash"></i></button>
                                <a href={`/Post?id=${item.id}`} className='btn btn-outline-primary mx-2'><i className="fa-solid fa-envelopes-bulk"></i></a>   
                            </div>
                            <TablePostAndComment
                                comments={item.comments}
                                value={props.value}
                            />
                        </div>
                    </div>
                )
            }
            <ModalEdit visible={modalTable} setVisible={setModalTable}>
                <form className="g-3 fs-4 description fw-bold container" id="frm-items-edit" onSubmit={(e) => handleSubmitEdit(e, currEditItem)}>
                    <div className="row">
                        <label className="form-label" htmlFor="priceEdit">Комментарий</label>
                        <input value={text} onChange={e => setText(e.target.value)} className="form-control" name='priceEdit' id="priceEdit" type="text" placeholder="Введите содержимое комментария" required />
                    </div>
                    <div className="text-center mt-3">
                        <button className="btn btn-primary mx-1" type="submit" id="buttonSaveChanges">Сохранить изменения</button>
                        <button className="btn btn-secondary mx-1" type="button" data-bs-dismiss="modal" onClick={() => setModalTable(false)}>Отмена</button>
                    </div>
                </form>
            </ModalEdit>
        </div>
    );
}