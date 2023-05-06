import { useState, useEffect } from "react";
import Toolbar from "../common/Toolbar";
import Table from "../common/Card";
import Modal from "../common/Modal";

export default function Catalog(props) {
    const [items, setItems] = useState([]);
    const [userId,setUserId] = useState(-1);
    const [value,setvalue]=useState('');

    useEffect(() => {
        loadItems();
    }, []);
    useEffect(() => {
        console.log("random");
    }, [items]);

    useEffect(()=>
    {
        if(userId==-1)
        {
            return;
        }
       loadItems1();
    },[userId])

    const getTokenForHeader = function () {
        return "Bearer " + localStorage.getItem("token");
    }
    useEffect(()=>
    {
        if(value=="")
        {
            return;
        }
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
        const requestUrl = `http://localhost:8080/api/1.0/post`;
        const response = await fetch(requestUrl,requestParams);
        const posts = await response.json();
       
        setItems(posts);   
    }
    const loadItems1 = async function() {
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
        setItems(posts);   
    }


    return (
        <>
            <div className="mx-3 my-2"><input type="text" id="search" className="form-control" required onChange={(event)=> setvalue(event.target.value)}/></div>
            <Toolbar 
                getUser={setUserIDd}
                />
            <Table 
                headers={props.headers} 
                items={items}
                value={value}
                userId={userId}
                selectable={true}/>
        </>
    );
}