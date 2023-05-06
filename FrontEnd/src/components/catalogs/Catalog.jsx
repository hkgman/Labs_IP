import { useState, useEffect } from "react";
import Toolbar from "../common/Toolbar";
import Table from "../common/Card";
import Modal from "../common/Modal";

export default function Catalog(props) {
    const [items, setItems] = useState([]);
    const [modalHeader, setModalHeader] = useState('');
    const [modalConfirm, setModalConfirm] = useState('');
    const [modalVisible, setModalVisible] = useState(false);
    const [isEdit, setEdit] = useState(false);
    const [userId,setUserId] = useState(0);
    const [value,setvalue]=useState('');
    useEffect(() => {
        loadItems();
    }, []);

    useEffect(()=>
    {
       loadItems();
    },[userId])

    const getTokenForHeader = function () {
        return "Bearer " + localStorage.getItem("token");
    }
    useEffect(()=>
    {
        loadItems2();
    },[value])
    const setUserIDd = async function(id)
    {
        setUserId(id);
    }
    const loadItems = async function() {
        const requestParams = {
            method: "GET",
            headers: {
                "Authorization": getTokenForHeader(),
            }
        };
        const requestUrl = `http://localhost:8080/api/1.0/user/${userId}/posts`;
        const response = await fetch(requestUrl,requestParams);
        const posts = await response.json();
        setItems(posts);   
    }

    const loadItems2 = async function() {
        const requestParams = {
            method: "GET",
            headers: {
                "Authorization": getTokenForHeader(),
            }
        };
        const requestUrl = `http://localhost:8080/api/1.0/post/filteredposts?Text=${value}`;
        const response = await fetch(requestUrl,requestParams);
        const posts = await response.json();
        console.log(posts);
        setItems(posts);   
    }

    const saveItem = async function() {
        if (!isEdit) {
            const requestUrl = `http://localhost:8080/api/1.0/user/${userId}/Post`;
            const temppost=JSON.stringify(props.data)
            const requestParams = {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: temppost,
            };
            await fetch(requestUrl,requestParams).then(() => loadItems());

        } else {
            const requestUrl = "http://localhost:8080/api/1.0/post/"+props.data.id;
            const temppost=JSON.stringify(props.data)
            const requestParams = {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: temppost,
            };
            await fetch(requestUrl,requestParams).then(() => loadItems());
        }
    }

    function handleAdd() {
        setEdit(false);
        setModalHeader('Добавление элемента');
        setModalConfirm('Добавить');
        setModalVisible(true);
        props.onAdd();
    }
    

    const edit = async function(editedId) {
        const requestUrl = "http://localhost:8080/api/1.0/post/"+editedId;
        const requestParams = {
            mode: 'cors'
        }
        await fetch(requestUrl,requestParams)
            .then(data => {
                setEdit(true);
                setModalHeader('Редактирование элемента');
                setModalConfirm('Сохранить');
                setModalVisible(true);
                //props.onEdit(data);
                return data.json();
            }).then(data =>{
                props.onEdit(data);
            });
    }

    const handleRemove = async function(id) {
            if (confirm('Удалить выбранные элементы?')) {
                const requestUrl = `http://localhost:8080/api/1.0/user/${userId}/Post/`+id;
                const requestParams = {
                    method: "DELETE",
                     headers: {
                        "Content-Type": "application/json",
                    },
                };
                await fetch(requestUrl,requestParams).then(()=>loadItems());
        }
    }



    function handleModalHide() {
        setModalVisible(false);
    }

    function handleModalDone() {
        saveItem();
    }
    

    return (
        <>
            <div className="mx-3 my-2"><input type="text" id="search" className="form-control" required onChange={(event)=> setvalue(event.target.value)}/></div>
            <Toolbar 
                onAdd={handleAdd}
                getUser={setUserIDd}
                />
            <Table 
                headers={props.headers} 
                items={items}
                value={value}
                userId={userId}
                selectable={true}
                onEdit={edit}
                onRemove={handleRemove}/>
            <Modal 
                header={modalHeader}
                confirm={modalConfirm}
                visible={modalVisible} 
                onHide={handleModalHide}
                onDone={handleModalDone}>
                    {props.children}
            </Modal>
        </>
    );
}