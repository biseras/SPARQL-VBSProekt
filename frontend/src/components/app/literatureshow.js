import React from 'react';
import Service from "../../repository/Repository";
import {useLocation} from "react-router-dom";
import { Link } from "react-router-dom";

const SearchResult = ({ data }) => {
    const location=useLocation()
    const mydata=location.state
    const onFormSubmit =async (e) => {
        e.preventDefault();
        console.log(mydata)
    }
    return (
        <div style={{ backgroundColor: "#f8f9fa", color: "#212529", margin: "50px", padding: "20px"}}>
            <p style={{ textAlign: "center", fontSize: "20px" }}>
                There have been many different literary movements throughout history, each with its own unique characteristics and contributions to the world of literature. Some of the most well-known literary movements include Romanticism, Realism, Naturalism, Modernism, and Postmodernism. Romanticism, for example, emphasized emotion, individualism, and the beauty of nature, while Realism sought to depict the world as it really is, often with a focus on the lives of ordinary people. Naturalism, on the other hand, portrayed humans as being shaped by their environment and biology, while Modernism and Postmodernism explored new forms of expression and challenged traditional notions of narrative and authorship. If you're interested in learning more about these literary movements and the works they produced, click the buttons below to explore some of our curated collections of literature.
            </p>
            <div style={{ textAlign: "center", margin: "50px 0" }}>
                <Link className={"btn btn-secondary m-2"} to={"/romanticism"}>Explore Romanticism</Link>
                <Link className={"btn btn-secondary m-2"} to={"/literaryrealism"}>Explore Realism</Link>
                <Link className={"btn btn-secondary m-2"} to={"/modernism"}>Explore Modernism</Link>
                <Link className={"btn btn-secondary m-2"} to={"/postmodernism"}>Explore Postmodernism</Link>
            </div>
            <p style={{ fontSize: "18px" }}>
                In addition to the literary movements mentioned above, there are many others that have played a significant role in shaping the world of literature. For example, the Beat Generation of the 1950s and 60s rebelled against traditional social and literary norms, while the Harlem Renaissance of the 1920s and 30s celebrated the unique experiences and cultural contributions of African Americans. The Existentialist movement of the mid-20th century explored themes of alienation, freedom, and the search for meaning in a chaotic world. These and many other literary movements have left an indelible mark on the literary landscape, and their works continue to be studied and celebrated by readers and scholars around the world.
            </p>
        </div>
    );
};

export default SearchResult;