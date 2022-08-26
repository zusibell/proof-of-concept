package com.example.backend.rs;

import com.example.backend.domain.Place;
import com.example.backend.domain.rs.ResponseMessage;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/places")
public class PlaceController {

    List<Place> places = Stream.of(
            new Place(1, "Frankfurt", "Germany"),
                new Place(2, "Montreal", "Canada")
        ).collect(Collectors.toList());

    @GetMapping("/all")
    public List<Place> getPlaces() {
        return places;
    }

    @GetMapping("/{id}")
    public Place getPlace(@PathVariable("id") int id) {
        return places.stream().filter(place -> place.getId() == id).findFirst().orElseThrow();
    }

    @PostMapping("/create")
    public ResponseMessage createPlace(Place place){
        var maxId = places.stream().max(Comparator.comparing(p -> p.getId())).get().getId();
        place.setId(maxId + 1 );
        places.add(place);
        return new ResponseMessage("Place created");
    }

    @PutMapping("/update/{id}")
    public ResponseMessage updatePlace(@PathVariable("id") int id, Place newPlace){
        var updatedPlace = places.stream().filter(place -> place.getId() == id).findFirst().orElseThrow();
        //updatedPlace.setId(newPlace.getId());
        updatedPlace.setCity(newPlace.getCity());
        updatedPlace.setCountry(newPlace.getCountry());
        places.add(updatedPlace);
        return new ResponseMessage("Place updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseMessage deletePlace(@PathVariable("id") int id) {
        var place = places.stream().filter(p -> p.getId() == id).findFirst().orElseThrow();
        places.remove(place);
        return new ResponseMessage("Place deleted");
    }

}
