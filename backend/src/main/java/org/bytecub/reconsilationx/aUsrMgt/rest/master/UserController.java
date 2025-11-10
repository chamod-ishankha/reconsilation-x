package org.bytecub.reconsilationx.aUsrMgt.rest.master;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.reconsilationx.aUsrMgt.dto.common.ResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MUserDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.miscellaneous.ApiResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.service.master.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // get user by id
    @GetMapping("/{userId}")
    public ResponseEntity<MUserDto> getUserById(@PathVariable Long userId) {
        log.info("Inside user controller get user by id method");
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    // get all users
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<MUserDto>>> getUsers(
        @RequestParam(defaultValue = "0", required = false) int page,
        @RequestParam(defaultValue = "10", required = false) Integer perPage,
        @RequestParam(defaultValue = "", required = false) String search,
        @RequestParam(defaultValue = "followersId", required = false) String sort,
        @RequestParam(defaultValue = "desc", required = false) String direction
    ) {
        log.info("Inside user controller get all users method");
        return new ResponseEntity<>(userService.getUsers(page, perPage, search, sort, direction), HttpStatus.OK);
    }

    // update user
    @PutMapping("/{userId}")
    public ResponseEntity<MUserDto> updateUser(@PathVariable Long userId, @RequestBody MUserDto userDto) {
        log.info("Inside user controller update user method");
        return new ResponseEntity<>(userService.updateUser(userId, userDto), HttpStatus.OK);
    }

    // delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable Long userId) {
        log.info("Inside user controller delete user method");
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }

    //  get user details by token
    @GetMapping("/details")
    public ResponseEntity<MUserDto> getUserDetailsByToken(@RequestHeader("Authorization") String token) {
        log.info("Inside user controller get user details by token method");
        return new ResponseEntity<>(userService.getUserDetailsByToken(token), HttpStatus.OK);
    }

}
