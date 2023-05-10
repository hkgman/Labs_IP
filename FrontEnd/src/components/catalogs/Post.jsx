import React from "react";
import { useState } from 'react';
import {useEffect} from 'react';
import New from '../../models/NewDto';
import TableComment from "../common/TableComment";

export default function Post(props) {
    useEffect(() => {
        getAll();
    }, []);
    const [userId,setUserId] = useState();
    const commentInput = document.getElementById("commentText");



    const getAll = async function () {
        const queryString=window.location.search;
        const urlParams=new URLSearchParams(queryString);
        const id=urlParams.get('id');
        getCurrentPost(id).then(curPost => setad(curPost));
        getComments(id)
        getUserId();
    }



    const refresh = async function(id,user,text)
    {
        const requestParams={
        method:"PUT",
        headers:{
            "Content-Type":"application/json",
            "Authorization": getTokenForHeader(),
        }  
        };
        const response=await fetch(`http://localhost:8080/api/1.0/comment/${id}/curUser/${userId}/commentUser/${user}?Text=${text}`,requestParams);
        return await response.json();
    }

    const getTokenForHeader = function () {
        return "Bearer " + localStorage.getItem("token");
    }

    const create = async function (text, id) {
        const requestParams = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": getTokenForHeader(),
            }
        };
        const response = await fetch(`http://localhost:8080/api/1.0/post/${ad.id}/Comment/${id}?Text=${text}`,requestParams);
    }

    const getUserId = async function () {
        const requestParams = {
            method: "GET",
            headers: {
                "Authorization": getTokenForHeader(),
            }
        };
        let login = localStorage.getItem("user");
        const requestUrl = "http://localhost:8080/api/1.0" + `/userId?login=${login}`;
        const response = await fetch(requestUrl, requestParams);
        const user = await response.json();
        setUserId(user);
    }

    const getCurrentPost = async function (id) {
        const requestParams = {
            method: "GET",
            headers: {
                "Authorization": getTokenForHeader(),
            }
        };
        const requestUrl = "http://localhost:8080/api/1.0/post/"+id;
        const response = await fetch(requestUrl,requestParams);
        const product = await response.json();
        return product;
    }



    const remove = async function(id,user){
        const requestParams = {
            method: "DELETE",
            headers:{
                "Content-Type":"application/json",
                "Authorization": getTokenForHeader(),
            }
        };
        const requestUrl = `http://localhost:8080/api/1.0/post/${ad.id}/Comment/${id}/curUser/${userId}/commentUser/${user}`;
        const response=await fetch(requestUrl,requestParams);
        return await response.json;      
    };




    const getComments = async function(id)
    {
        const requestParams = {
            method: "GET",
            headers: {
                "Authorization": getTokenForHeader(),
            }
        };
        const requesturl = "http://localhost:8080/api/1.0/post/"+id+"/comments"
        const response = await fetch(requesturl,requestParams);
        const comments = await response.json();
        setComments(comments);
    }
    const [ad, setad] = useState([]);
    const [comments,setComments] = useState([]);


    const rem_but = function(id,user,event)
    {
        remove(id,user).then((result)=>{
            getAll();
        });
    }



    const add_but = function(event)
    {
        event.preventDefault();
            create(commentInput.value, userId).then((result) => {
                getAll();
                commentInput.value = "";
            });
    }


    const edit_btn = function(id,user,event)
    {
        console.log("Обновление")
            refresh(id,user, commentInput.value).then((result)=>{
                getAll();
                commentInput.value = "";
            });
    }
    //Комменты удаляются в любом случае (не важно какой пользователь его написал (надо фиксить))
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