package ru.leroy.screenerapi.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.leroy.screenerapi.dto.market.MarketResponseDto;
import ru.leroy.screenerapi.dto.market.create.MarketRequestCreate;
import ru.leroy.screenerapi.entity.MarketEntity;
import ru.leroy.screenerapi.exception.market.FullNameExistException;
import ru.leroy.screenerapi.exception.market.ShortNameExistException;
import ru.leroy.screenerapi.exception.market.ShortNameMatchFullNameException;
import ru.leroy.screenerapi.message.ResponseMessages;
import ru.leroy.screenerapi.service.MarketService;

/**
 * The type Market controller.
 */

@RestController
@RequestMapping("/market")
public class MarketController {
  private final MarketService service;

  public MarketController(final MarketService service) {
    this.service = service;
  }

  private final Logger log =
      LoggerFactory.getLogger(MarketController.class);

  private final ModelMapper modelMapper = new ModelMapper();

  @PostMapping("/create")
  public ResponseEntity<?> create (@RequestBody final MarketRequestCreate request) {
    final MarketEntity entity = this.modelMapper.map(request, MarketEntity.class);
    try {
      this.log.info("REST request to market create");
      final MarketEntity created = this.service.create(entity);
      final MarketResponseDto response =
          this.modelMapper.map(created, MarketResponseDto.class);
      return ResponseEntity
          .status(HttpStatus.CREATED)
          .body(response);
    } catch (final FullNameExistException ex) {
        this.logOnErrorCreation(ex);
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ex.getMessage());
    } catch (final ShortNameExistException ex) {
      this.logOnErrorCreation(ex);
      return ResponseEntity
          .status(HttpStatus.CONFLICT)
          .body(ex.getMessage());
    } catch (final ShortNameMatchFullNameException ex) {
      this.logOnErrorCreation(ex);
      return ResponseEntity
          .status(HttpStatus.CONFLICT)
          .body(ex.getMessage());
    } catch (final Exception ex) {
      this.logOnErrorCreation(ex);
      return badRequestResponse();
    }
  }

  private static ResponseEntity<String> badRequestResponse() {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ResponseMessages.UNEXPECTED_ERROR);
  }
  private void logOnErrorCreation (final Exception ex) {
    this.log.error(
        String.format(
            "Error in market creation: %s",
            ex.getMessage()
        )
    );
  }
}
