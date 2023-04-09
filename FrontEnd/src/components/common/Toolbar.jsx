import { useState } from 'react';
import {useEffect} from 'react';

export default function Toolbar(props) {
    function add() {
        props.onAdd();
    }
    const [clients, setClientst] = useState([]);
    
    useEffect(() => {
        getAll();
    }, []);
    const getAll = async function () {
        const requestUrl = "http://localhost:8080/user";
        const response = await fetch(requestUrl);
        const users = await response.json();
        setClientst(users);
    }
    function getuser(){
         var selectBox = document.getElementById("selectBox");
         var selectedValue = selectBox.options[selectBox.selectedIndex].value;
        props.getUser(selectedValue);
    }
    return (
        <div className="d-flex float-start my-2">
            <div className="mx-1">
                <select id="selectBox" onChange={getuser}>
                    <option  disabled value="">Выбор...</option>
                    {
                        clients.map((client) => (
                                <option className='text-black' key={client.id} value={client.id}>{client.firstName}</option>
                    ))}
                </select>
            </div>
            <div>
                <button type="button" className="btn btn-primary" onClick={add}>
                    +
                </button>
            </div>
            
        </div >
    );
}