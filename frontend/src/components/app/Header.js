import React from 'react';
import {Link, Route} from 'react-router-dom';
import Showromanticismbooks from "./showromanticismbooks";

const header = (props) => {
    return (
        <header>
            <nav className="navbar navbar-expand-md navbar-dark navbar-fixed bg-dark">
                <a className="navbar-brand" href="/"></a>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse"
                        aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarCollapse">
                    <ul className="navbar-nav mr-auto">
                        <li className="nav-item active">
                            <Link className={"nav-link"} to={"/searchauthor"}>Search Author</Link>
                            <Link className={"nav-link"} to={"/searchbook"}>Search Book</Link>
                            <Link className={"nav-link"} to={"/romanticism"}>Romanticism</Link>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>
    )
}

export default header;