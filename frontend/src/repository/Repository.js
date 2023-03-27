import axios from "../costum-axios/axios";
const Service={
    searchAuthor: (name) => {
        return axios.post("/searchauthor", {
            "name":name
        }).then(response => {
            return response.data;
        })
    }
}

export default Service;