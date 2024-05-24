package com.example.crudapp.controller;


import com.example.crudapp.model.book;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.crudapp.repo.bookrepo;

@RestController
public class bookcontroller {
    @Autowired
    private bookrepo bookrepo;

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<book>> getAllbooks(){
        try {
            List<book> booklList = new ArrayList<>();
            
            bookrepo.findAll().forEach(booklList::add);
            
            if (booklList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(booklList,HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getBookbyId/{id}")
    public ResponseEntity<book> getBookbyID(@PathVariable Long id){
        Optional<book> bookData =  bookrepo.findById(id);
         
        if(bookData.isPresent()){
            return new ResponseEntity<>(bookData.get(),HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addBook")
    public ResponseEntity<book> addBook(@RequestBody book book){
        book bookObj = bookrepo.save(book);

        return new ResponseEntity<>(bookObj, HttpStatus.OK);
    }

    @PostMapping("/updateBookById/{id}")
    public ResponseEntity<book> updateBookbyID(@PathVariable Long id, @RequestBody book newBook){
        Optional<book> oldBookData = bookrepo.findById(id);

        if(oldBookData.isPresent()){
            book updatedBookData = oldBookData.get();
            updatedBookData.setTitle(newBook.getTitle());
            updatedBookData.setAuthor(newBook.getAuthor());

            book bookonj = bookrepo.save(updatedBookData);

            return new ResponseEntity<>(bookonj, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }
    @DeleteMapping("deleteBookByID/{id}")
    public ResponseEntity<HttpStatus> deleteBookbyID(@PathVariable Long id){
        bookrepo.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
