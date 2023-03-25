import axios from "../costum-axios/axios";
const Service={
    searchAuthor: (name) => {
        return axios.post("/searchauthor", {
            "name" : name
        });
    }
}

export default Service;