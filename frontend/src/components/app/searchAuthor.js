import React, { useState } from 'react';
import Service from "../../repository/Repository";
import {Link, useNavigate} from 'react-router-dom';

const SearchAuthor = () => {
    const [responseState, updateData] = useState(null);
    const history = useNavigate();

    const handleSelectChange = async (e) => {
        const name = e.target.value;
        let response = await Service.searchAuthor(name)
        console.log(response)
        updateData(response.data)
        history('/result', { state: response });
    }

    return (
        <div style={{ backgroundColor: "#f8f9fa", color: "#212529", margin: "50px", padding: "20px"}}>
            <p style={{ fontSize: "26px", marginBottom: "20px" }}>Knowing about famous authors is an essential part of understanding the world of literature and expanding our knowledge. From Shakespeare to Jane Austen, Ernest Hemingway to Maya Angelou, the contributions of famous authors have shaped the literary landscape of the world. Their works have been celebrated for centuries and have continued to inspire new generations of writers. Therefore, it is important to appreciate their contributions and learn more about their lives and works. Through studying the famous authors, we can better understand the literary movements, cultural contexts, and historical events that have shaped our world.</p>
            <br/>
            <h1 style={{ fontSize: "36px", marginBottom: "20px" }}>Here you can find informations about famous authors</h1>
            <p style={{ fontSize: "25px", marginBottom: "20px" }}>Select an author from the list to see more information about them:</p>
            <div style={{ display: "flex", alignItems: "center", justifyContent: "center", flexDirection: "column" }}>
            <select
                className="form-control"
                id="name"
                name="name"
                onChange={handleSelectChange}
                style={{ fontSize: '24px', marginBottom: '20px', width: '500px', borderRadius: "5px", padding: "10px" }}>
                <option value="">Select an author</option>*/}
                <option value="Mark_Twain">Mark Twain</option>*/}
                <option value="Fyodor_Dostoevsky">Fyodor Dostoevsky</option>*/}
                <option value="Anton_Chekhov">Anton Chekhov</option>*/}
                <option value="Samuel_Beckett">Samuel Beckett</option>*/}
                <option value="Victor_Hugo">Victor Hugo</option>*/}
                <option value="Honoré_de_Balzac">Honoré de Balzac</option>*/}
                <option value="Ivo_Andrić">Ivo Andrić</option>*/}
                <option value="Ernest_Hemingway">Ernest Hemingway</option>*/}
                <option value="Miguel_de_Cervantes">Miguel de Cervantes</option>*/}
                <option value="Giovanni_Boccaccio">Giovanni Boccaccio</option>*/}
                <option value="Kočo_Racin">Kočo Racin</option>*/}
                <option value="Charles_Dickens">Charles Dickens</option>*/}
                <option value="Jules_Verne">Jules Verne</option>*/}
                <option value="Leo_Tolstoy">Leo Tolstoy</option>*/}
            </select>
                <button type="submit" className="btn btn-primary" style={{ fontSize: '24px', borderRadius: "5px", padding: "10px" }}>Submit</button>
            </div>
        </div>
    )
}

export default SearchAuthor;
