import { useState, useEffect } from "react";
import { useNavigate,Link } from 'react-router-dom';

const NavBar = function (props) {

    const [userRole, setUserRole] = useState("NONE");

    const navigate = useNavigate();

    useEffect(() => {
        window.addEventListener("storage", () => {
            let token = localStorage.getItem("token");
            if (token) {
                getRole(token).then((role) => {
                    if (localStorage.getItem("role") != role) {
                        localStorage.removeItem("token");
                        localStorage.removeItem("user");
                        localStorage.removeItem("role");
                        window.dispatchEvent(new Event("storage"));
                        navigate("/login");
                    }
                });
            }
            getUserRole();
        });
        getUserRole();
    }, [])

    const getRole = async function (token) {
        const requestParams = {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        };
        const requestUrl = `http://localhost:8080/who_am_i?token=${token}`;
        const response = await fetch(requestUrl, requestParams);
        const result = await response.text();
        return result;
    }


    

    const getUserRole = function () {
        const role = localStorage.getItem("role") || "NONE";
        setUserRole(role);
    }

    const validate = function (userGroup) {
        if ((userGroup === "AUTH" && userRole !== "NONE") ||
            (userGroup === userRole)) {
            return true;
        }
        return false;
    }

    return (
        <header className="fs-4 fw-bold p-1 text-white bg-primary bg-gradient">
                    <div><img className="img-fluid float-start" src="../img/Emblema.png" alt="Emblema"/>
                        <p className="fs-5 Cont">Муниципальное бюджетное общеобразовательное учреждение средняя общеобразовательная школа №10</p>
                    </div>
                    <nav className="navbar navbar-expand-md navbar-dark">
                        <div className="container-fluid">
                            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation"> <span className="navbar-toggler-icon"></span></button>
                            <div className="navbar-collapse collapse justify-content-end" id="navbarNav">
                                <nav className="headers-problem navbar navbar-expand-lg d-flex">
                                    {props.links.map(route => {
                                        if (validate(route.userGroup)) {
                                            return (
                                            <button key={route.path} className="btn btn-outline-light mx-1">
                                                <Link className="nav-link" to={route.path}>
                                                    {route.label}
                                                </Link>
                                            </button> );
                                        }
                                    })}
                                </nav>
                            </div>
                        </div>
                    </nav>
                </header>
    );
}

export default NavBar;
