import React from "react";
import { useState } from 'react';
import {useEffect} from 'react';
import New from '../../models/NewDto';
import TableComment from "../common/TableComment";

export default function Post(props) {
    useEffect(() => {
        getAll();
    }, []);
    const [output, setOutput] = useState([]);
    const [userId,setUserId] = useState();
    const commentInput = document.getElementById("commentText");



    const getAll = async function () {
        const queryString=window.location.search;
        const urlParams=new URLSearchParams(queryString);
        const id=urlParams.get('id');
        getCurrentPost(id).then(curPost => setad(curPost));
        getComments(id)

        const requestUrl = "http://localhost:8080/user";
        const response = await fetch(requestUrl);
        const users = await response.json();
        setOutput(users);
    }



    const refresh = async function(id,text)
        {
            const requestParams={
              method:"PUT",
              headers:{
                "Content-Type":"application/json",
              }  
            };
            const response=await fetch(`http://localhost:8080/comment/${id}?Text=${text}`,requestParams);
            return await response.json();
        }



    const create = async function (text, id) {
        const requestParams = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            }
        };
        const response = await fetch(`http://localhost:8080/post/${ad.id}/Comment/${id}?Text=${text}`,requestParams);
    }



    const getCurrentPost = async function (id) {
        const requestUrl = "http://localhost:8080/post/"+id;
        const response = await fetch(requestUrl);
        const product = await response.json();
        return product;
    }



    const remove = async function(id){
        const requestParams = {
            method: "DELETE",
            headers:{
                "Content-Type":"application/json",
            }
        };
        console.log(id);
        const requestUrl = `http://localhost:8080/post/${ad.id}/Comment/${id}`;
        const response=await fetch(requestUrl,requestParams);
        return await response.json;      
    };


    function getuser(){
        var selectBox = document.getElementById("selectBox");
        var selectedValue = selectBox.options[selectBox.selectedIndex].value;
        setUserId(selectedValue);
   }


    const getComments = async function(id)
    {
        const requesturl = "http://localhost:8080/post/"+id+"/comments"
        const response = await fetch(requesturl);
        const comments = await response.json();
        setComments(comments);
    }
    const [ad, setad] = useState([]);
    const [comments,setComments] = useState([]);


    const rem_but = function(id,event)
    {
        console.log("Удаление")
        remove(id).then((result)=>{
            getAll();
        });
    }



    const add_but = function(event)
    {
        event.preventDefault();
            create(commentInput.value, userId).then((result) => {
                getAll();
                commentInput.value = "";
                alert(`Comment[id=${result.id}, text=${result.firstName}]`);
            });
    }


    const edit_btn = function(id,event)
    {
        console.log("Обновление")
            refresh(id, commentInput.value).then((result)=>{
                getAll();
                commentInput.value = "";
            });
    }
    return(
        <div>
            <div className="da d-flex my-2">
                <div><img className="imga img-fluid float-start mx-2" style={{width: "500px" ,height: "300px" }} src={ad.image}/></div>
                <div className="container-fluid my-2 mx-1">
                    <h1>{ad.heading}</h1>
                    <p>{ad.content}</p>
                </div>
            </div>
            <div className="d-flex mx-2">
                <select id="selectBox"  onChange={getuser}>
                    <option  disabled value="">Выбор...</option>
                    {
                        output.map((client) => (
                                <option className='text-black' key={client.id} value={client.id}>{client.firstName}</option>
                    ))}
                </select>
                <input className="form-control" id="commentText" type="text" defaultValue="" required/>
                <button type="button" className="btn btn-primary" onClick={(e)=>add_but(e)}>
                    +
                </button>
            </div>
            <div className="row table-responsive mx-2">
                    <TableComment
                        comments={comments}
                        onRemove={rem_but}
                        onEdit={edit_btn}
                    />
            </div>
    </div>
        
    );

}