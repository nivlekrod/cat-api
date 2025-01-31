package com.gftstart.cat_api.controller;

import com.gftstart.cat_api.model.Cat;
import com.gftstart.cat_api.service.CatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "cat-api")
public class CatController {

    @Autowired
    private CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    @Operation(summary = "Busca um gato pelo ID", description = "Retorna um gato específico com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gato encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Gato não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao acessar o banco de dados")
    })
    @GetMapping("/cats/{id}")
    public ResponseEntity<Cat> getCat(@PathVariable("id") Long id) {
        Cat cat = catService.getCat(id);
        return ResponseEntity.ok(cat);
    }

    @Operation(summary = "Lista todos os gatos", description = "Retorna uma lista de todos os gatos registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de gatos recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum gato encontrado")
    })
    @GetMapping("/cats")
    public ResponseEntity<List<Cat>> getAllCats() {
        List<Cat> cats = catService.getAllCats();
        return ResponseEntity.ok(cats);
    }

    @Operation(summary = "Filtra gatos pela raça", description = "Retorna uma lista de gatos filtrada pela raça informada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de gatos filtrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum gato encontrado para a raça informada")
    })
    @GetMapping("/cats/filter")
    public ResponseEntity<List<Cat>> searchCatsByBreed(@RequestParam String breed) {
        List<Cat> cats = catService.searchCatsByBreed(breed);
        return ResponseEntity.ok(cats);
    }

    @Operation(summary = "Cria um novo gato", description = "Registra um novo gato no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Gato criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar gato")
    })
    @PostMapping("/cats")
    public ResponseEntity<Cat> createCat(@RequestBody Cat cat) {
        Cat createdCat = catService.createCat(cat);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCat);
    }

    @Operation(summary = "Atualiza um gato", description = "Atualiza os dados de um gato com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gato atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Gato não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar o gato no banco de dados")
    })
    @PutMapping("/cats/{id}")
    public ResponseEntity<Cat> updateCat(@PathVariable("id") Long id, @RequestBody Cat cat) {
        Cat updatedCat = catService.updateCat(id, cat);
        return ResponseEntity.ok(updatedCat);
    }

    @Operation(summary = "Deleta um gato", description = "Remove um gato do sistema com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Gato removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Gato não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao acessar o banco de dados")
    })
    @DeleteMapping("/cats/{id}")
    public ResponseEntity<String> deleteCat(@PathVariable("id") Long id) {
        catService.deleteCat(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Lista todas as raças", description = "Retorna uma lista de todas as raças disponíveis no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de raças recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma raça encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro ao acessar API externa")
    })
    @ResponseBody
    @GetMapping("/breeds")
    public ResponseEntity<List<String>> getAllBreeds() {
        List<String> breeds = catService.getAllBreeds();
        return ResponseEntity.ok(breeds);
    }

    @Operation(summary = "Obtém a imagem de uma raça", description = "Retorna a URL da imagem correspondente à raça informada.")
    @ApiResponse(responseCode = "200", description = "Imagem recuperada com sucesso")
    @GetMapping("/image")
    public ResponseEntity<String> getBreedImage(@RequestParam("breed") String breed) {
        String imageUrl = catService.getBreedImage(breed);
        return ResponseEntity.ok(imageUrl);
    }
}