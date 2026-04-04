package info.jab.ms.gods.controller;

import info.jab.ms.gods.api.GreekGodsApi;
import info.jab.ms.gods.service.GreekGodsService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreekGodsController implements GreekGodsApi {

	private final GreekGodsService greekGodsService;

	public GreekGodsController(GreekGodsService greekGodsService) {
		this.greekGodsService = greekGodsService;
	}

	@Override
	public ResponseEntity<List<String>> getGreekGods() {
		return ResponseEntity.ok(greekGodsService.findAllNamesOrdered());
	}
}
