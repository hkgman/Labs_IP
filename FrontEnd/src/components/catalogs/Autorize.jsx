import React from "react";
import { useState } from 'react';
import {useEffect} from 'react';
import UserDto from '../../models/UserDto';
export default function CatalogGroups(props) {
    const formRef = React.createRef();
    const [output, setOutput] = useState([]);
    const FirstInput = document.getElementById("validationCustom01");
    const LastInput = document.getElementById("validationCustom02");
    const emailInput = document.getElementById("validationCustomMail");
    const getAll = async function () {
        const requestUrl = "http://localhost:8080/user";
        const response = await fetch(requestUrl);
        const users = await response.json();
        setOutput(users);
    }
    useEffect(() => {
        getAll();
    }, []);
    const remove = async function(id){
        const requestParams = {
            method: "DELETE",
            headers:{
                "Content-Type":"application/json",
            }
        };
        const requestUrl = "http://localhost:8080" + "/user/" + id;
        const response=await fetch(requestUrl,requestParams);
        return await response.json;      
    };
    const create = async function (firstName, lastName,Email) {
        const requestParams = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            }
        };
        const response = await fetch("http://localhost:8080" + `/user?firstName=${firstName}&lastName=${lastName}&email=${Email}`, requestParams);
        return await response.json(); 
    }
    const refresh = async function(id,firstName, lastName,Email)
        {
            const requestParams={
              method:"PUT",
              headers:{
                "Content-Type":"application/json",
              }  
            };
            const response=await fetch(`http://localhost:8080/user/${id}?firstName=${firstName}&lastName=${lastName}&email=${Email}`,requestParams);
            return await response.json();
        }
    const add_but = function(event)
    {
        event.preventDefault();
            create(FirstInput.value, LastInput.value,emailInput.value).then((result) => {
                getAll();
                FirstInput.value = "";
                LastInput.value = "";
                emailInput.value="";
                alert(`User[id=${result.id}, firstName=${result.firstName}, lastName=${result.lastName},email=${result.email}]`);
            });
    }
    const edit_btn = function(id,event)
    {
        console.log("Обновление")
            refresh(id,FirstInput.value, LastInput.value,emailInput.value).then((result)=>{
                getAll();
                FirstInput.value = "";
                LastInput.value = "";
                emailInput.value="";
            });
    }
    const rem_but = function(id,event)
    {
        console.log("Удаление")
            remove(id).then((result)=>{
                getAll();
            });
    }
    
    return (
        <div className="p-2">
            <form className="row g-3 needs-validation">
                <div className="col-md-4">
                <label className="form-label" htmlFor="validationCustom01">Имя</label>
                <input className="form-control" id="validationCustom01" type="text" defaultValue="" required/>
                <div className="valid-feedback">Отлично!</div>
                <div className="invalid-feedback">Введите имя!</div>
                </div>
                <div className="col-md-4">
                <label className="form-label" htmlFor="validationCustom02">Фамилия</label>
                <input className="form-control" id="validationCustom02" type="text" defaultValue="" required/>
                <div className="valid-feedback">Отлично!    </div>
                <div className="invalid-feedback">Введите Фамилию!</div>
                </div>
                <div className="col-md-4">
                <label className="form-label" htmlFor="validationCustomMail">Почта</label>
                <div className="input-group has-validation">
                    <input className="form-control" id="validationCustomMail" type="email" aria-describedby="inputGroupPrepend" required/>
                    <div className="valid-feedback">Отлично!</div>
                    <div className="invalid-feedback">Введите почту!</div>
                </div>
                </div>
                <div className="col-12">
                <div className="form=check">
                    <input className="form-check-input" id="invalidCheck" type="checkbox" defaultValue="" required/>
                    <label className="form-check-label" htmlFor="invalidCheck">Согласие на обработку персональных данных</label>
                    <div className="valid-feedback">Отлично!</div>
                    <div className="invalid-feedback">Введите пароль!</div>
                </div>
                </div>
                <div className="col-12 d-flex">
                    <div className="d-grid col-sm-4 mx-auto">
                        <button type="button" id="add_btn" className="btn btn-outline-light btn-lg float-end" onClick={add_but}>Add</button>
                    </div>
                </div>
            </form>
            <div className="row table-responsive mx-2">
                <table className="table mt-3">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Last Name</th>
                            <th scope="col">First Name</th>
                            <th scope="col">Email</th>
                            <th scope="col"></th>
                            <th scope="col"></th>
                        </tr>
                    </thead>
                    <tbody id="tbody">
                        {output.map((user) => (
                                <tr key={user.id}>
                                    <th scope="row">{user.id}</th>
                                    <td>{user.firstName}</td>
                                    <td>{user.lastName}</td>
                                    <td>{user.email}</td>
                                    <td><button type="button" className="btn btn-outline-light text-center mx-2" onClick={(e) => edit_btn(user.id, e)}><i className="fa-sharp fa-solid fa-pen"></i></button></td>
                                    <td><button type="button" className="btn btn-outline-light text-center mx-2" onClick={(e) => rem_but(user.id, e)}><i className="fa-sharp fa-solid fa-trash"></i></button></td>
                                </tr>
                            ))}
                    </tbody>
        </table>
    </div>
        </div>
    );
}