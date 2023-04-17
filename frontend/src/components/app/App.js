import React from "react";
import SearchAuthor from "./searchAuthor";
import Showromanticismbooks from "./showromanticismbooks";
import Showrelbooks from "./showbooksrealism";
import Showmodbooks from "./showbooksmodernisam";
import Showpostmodbooks from "./showpostmodbooks"
import SearchBook from "./searchBook";
import {Component} from "react";
import Header from "./Header";
import {
  Routes,
  Navigate,
  Route, Router
} from 'react-router-dom';
import SearchResult from './searchResult';
import Show from './literatureshow';
import SearchResultBook from './searchResultBook';
import Service from "../../repository/Repository";

class App extends Component{
    constructor(props) {
        super(props);
        this.state={
            author:[],
            book:[],
            booksrom:[],
            booksrel:[],
            booksmod:[],
            bookspostmod:[]
        }
    }
  render() {
    return (
        <div>
          <Header/>
          <div className={"container"}>
            <Routes>
                <Route path="/searchauthor" element={<SearchAuthor onsearch={this.searchAuthor} />} />
                <Route path="/result" element={<SearchResult/>} />
                <Route path="/searchbook" element={<SearchBook onsearch={this.searchBook} />} />
                <Route path="/resultbook" element={<SearchResultBook/>} />
                <Route path="/romanticism"  exact element={<Showromanticismbooks show={this.state.booksrom}/>}/>
                <Route path="/literaryrealism"  exact element={<Showrelbooks show={this.state.booksrel}/>}/>
                <Route path="/modernism"  exact element={<Showmodbooks show={this.state.booksmod}/>}/>
                <Route path="/postmodernism"  exact element={<Showpostmodbooks show={this.state.bookspostmod}/>}/>
                <Route path="/literature" element={<Show/>} />
            </Routes>
          </div>
        </div>
    )
  }
    componentDidMount() {
        this.booksrom();
        this.booksrel();
        this.booksmod();
        this.bookspostmod();
    }
  searchAuthor = (name) => {
    Service.searchAuthor(name)
        .then((data)=>{

          this.setState({
              author:data.data
          })
      });
  }
    booksrom = () => {
        Service.booksromanticism()
            .then((data)=>{
                this.setState({
                    booksrom:data.data
                })
            });
    }
    booksrel = () => {
        Service.booksliterary_realism()
            .then((data)=>{
                this.setState({
                    booksrel:data.data
                })
            });
    }
    booksmod = () => {
        Service.booksliterary_modernisam()
            .then((data)=>{
                this.setState({
                    booksmod:data.data
                })
            });
    }
    bookspostmod = () => {
        Service.booksliterary_postmodernisam()
            .then((data)=>{
                this.setState({
                    bookspostmod:data.data
                })
            });
    }
  searchBook = (name) => {
        Service.searchBook(name)
            .then((data)=>{

                this.setState({
                    book:data.data
                })
            });
    }
    lit = () => {

    }
}

export default App;

