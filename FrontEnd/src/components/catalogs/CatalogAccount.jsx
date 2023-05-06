import { useState, useEffect } from "react";
import Toolbar from "../common/ToolbarAccount";
import Table from "../common/CardForNews";
import Modal from "../common/Modal";
const hostURL = "http://localhost:8080";
const host = hostURL + "/api/1.0";
export default function CatalogAccount(props) {
    const [items, setItems] = useState([]);
    const [modalHeader, setModalHeader] = useState('');
    const [modalConfirm, setModalConfirm] = useState('');
    const [modalVisible, setModalVisible] = useState(false);
    const [isEdit, setEdit] = useState(false);
    const [userId,setUserId]=useState(0);

    useEffect(() => {
        getUserId();
    }, []);
    useEffect(() => {
        if(userId==0)
        {
            return;
        }
        loadItems();
    }, [userId]);

    const getTokenForHeader = function () {
        return "Bearer " + localStorage.getItem("token");
    }
    const getUserId = async function () {
        const requestParams = {
            method: "GET",
            headers: {
                "Authorization": getTokenForHeader(),
            }
        };
        let login = localStorage.getItem("user");
        const requestUrl = host + `/userId?login=${login}`;
        const response = await fetch(requestUrl, requestParams);
        const user = await response.json();
        setUserId(user);
    }
    const loadItems = async function() {
        const requestParams = {
            method: "GET",
            headers: {
                "Authorization": getTokenForHeader(),
            }
        };
        console.log(userId);
        const requestUrl = `http://localhost:8080/api/1.0/user/${userId}/posts`;
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
                    "Authorization": getTokenForHeader(),
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
                    "Authorization": getTokenForHeader(),
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
            mode: 'cors',
            headers: {
                "Authorization": getTokenForHeader(),
            },
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
                        "Authorization": getTokenForHeader(),
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
            <Toolbar 
                onAdd={handleAdd}
                />
            <Table 
                headers={props.headers} 
                items={items}
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