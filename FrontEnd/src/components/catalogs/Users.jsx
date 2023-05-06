import { useState, useEffect } from "react";

const hostURL = "http://localhost:8080";
const host = hostURL + "/api/1.0";

const Users = function () {

    const [users, setUsers] = useState([]);
    const [pageNumbers, setPageNumbers] = useState([]);
    const [pageNumber, setPageNumber] = useState();

    useEffect(() => {
        getUsers(1);
    }, []);

    const getTokenForHeader = function () {
        return "Bearer " + localStorage.getItem("token");
    }

    const getUsers = async function (page) {
        const requestParams = {
            method: "GET",
            headers: {
                "Authorization": getTokenForHeader(),
            }
        };
        const requestUrl = host + `/users?page=${page}`;
        const response = await fetch(requestUrl, requestParams);
        const data = await response.json();
        setUsers(data.first.content);
        setPageNumber(data.first.number);
        setPageNumbers(data.second);
    }

    const removeUser = async function (id) {
        const requestParams = {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "Authorization": getTokenForHeader(),
            }
        };
        const requestUrl = host + `/user/${id}`;
        await fetch(requestUrl, requestParams);
    }

    const pageButtonOnClick = function (page) {
        getUsers(page);
    }

    const removeButtonOnClick = function (id) {
        const confirmResult = confirm("Are you sure you want to remove " + 
            "the selected user?");
        if (confirmResult === false) {
            return;
        }
        removeUser(id).then(() => getUsers(pageNumber + 1));
    }

    return (
        <>
            <div className="table-shell mx-3">
                <table className="table">
                    <thead>
                    <tr>
                        <th style={{ width: "10%" }} scope="col">#</th>
                        <th style={{ width: "15%" }} scope="col">ID</th>
                        <th style={{ width: "30%" }} scope="col">Login</th>
                        <th style={{ width: "30%" }} scope="col">Email</th>
                        <th style={{ width: "15%" }} scope="col">Role</th>
                    </tr>
                    </thead>
                    <tbody>
                        {users.map((user, index) => (
                            <tr>
                                <th style={{ width: "10%" }} scope="row">{index}</th>
                                <td style={{ width: "15%" }}>{user.id}</td>
                                <td style={{ width: "30%" }}>{user.login}</td>
                                <td style={{ width: "30%" }}>{user.email}</td>
                                <td style={{ width: "15%" }}>{user.role}</td>
                                {user.login !== localStorage.getItem("user") ?
                                    <td style={{ width: "1%" }}>
                                        <button className="btn btn-secondary btn-sm"
                                                onClick={() => removeButtonOnClick(user.id)}>
                                            del
                                        </button>
                                    </td> : null}
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <div>
                <p>
                    Pages:
                </p>
                <nav>
                    <ul className="pagination">
                        {pageNumbers.map((number) => (
                            <li className={`page-item ${number === pageNumber + 1 ? "active" : ""}`}
                                onClick={() => pageButtonOnClick(number)}>
                                <a className="page-link" href="#">{number}</a>
                            </li>
                        ))}
                    </ul>
                </nav>
            </div>
        </>
    );
}

export default Users;