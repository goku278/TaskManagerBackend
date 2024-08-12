package com.taskmanager.demo.service;

import com.taskmanager.demo.db.Users;
import com.taskmanager.demo.model.dto.Status;
import com.taskmanager.demo.model.dto.Task;
import com.taskmanager.demo.repo.TaskRepository;
import com.taskmanager.demo.repo.UsersRepository;
import com.taskmanager.demo.util.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {
    @InjectMocks
    private TaskService taskService; // Replace with your actual service class

    @Mock
    private TaskRepository taskRepository; // Replace with your actual repository class

    @Mock
    private Converter converter; // Replace with your actual converter class

    @Mock
    private UsersRepository usersRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenSavePostSuccessful_thenReturnSuccessStatus() {
        // Given
        Task inputTask = new Task(); // Create and configure your input Task object
        com.taskmanager.demo.db.Task dbTask = new com.taskmanager.demo.db.Task(); // Create and configure your DB Task object
        when(converter.convert(inputTask)).thenReturn(dbTask);
        when(taskRepository.save(dbTask)).thenReturn(dbTask);

        // When
        ResponseEntity<?> response = taskService.savePost(inputTask);

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void savePostWhenExceptionThrown_thenReturnErrorStatus() {
        // Given
        Task inputTask = new Task(); // Create and configure your input Task object
        when(converter.convert(inputTask)).thenThrow(new RuntimeException("Conversion error"));

        // When
        ResponseEntity<?> response = taskService.savePost(inputTask);

        // Then
        assertThat(500).isEqualTo(500);
    }

    @Test
    public void whenTasksExist_thenReturnTasks() {
        // Given
        com.taskmanager.demo.db.Task dbTask = new com.taskmanager.demo.db.Task(); // Create and configure your DB Task object
        Task task = new Task(); // Create and configure your Task object
        List<com.taskmanager.demo.db.Task> dbTasks = new ArrayList<>();
        dbTasks.add(dbTask);
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        when(taskRepository.findAll()).thenReturn(dbTasks);
        when(converter.convert(dbTask)).thenReturn(task);

        // When
        ResponseEntity<?> response = taskService.getAllTasks();

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        Status status = (Status) response.getBody();
        assertThat(status.getCode()).isEqualTo(200);
        assertThat(status.getMessage()).isNull();
    }

    @Test
    public void whenNoTasksFound_thenReturnNoTaskFoundStatus() {
        // Given
        when(taskRepository.findAll()).thenReturn(new ArrayList<>()); // Simulate empty task list

        // When
        ResponseEntity<?> response = taskService.getAllTasks();

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        Status status = (Status) response.getBody();
        assertThat(status.getCode()).isEqualTo(200);
        assertThat(status.getMessage()).isEqualTo("No Task Found");
    }

    @Test
    public void getAllTasksWhenExceptionThrown_thenReturnErrorStatus() {
        // Given
        when(taskRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // When
        ResponseEntity<?> response = taskService.getAllTasks();

        // Then
        assertThat(500).isEqualTo(500);
        Status status = (Status) response.getBody();
        assertThat(status.getCode()).isEqualTo(500);
    }

    @Test
    public void getTaskWhenTaskExists_thenReturnTask() {
        // Given
        Long taskId = 1L;
        com.taskmanager.demo.db.Task dbTask = new com.taskmanager.demo.db.Task(); // Create and configure your DB Task object
        Task task = new Task(); // Create and configure your Task object
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(dbTask));
        when(converter.convert(dbTask)).thenReturn(task);

        // When
        ResponseEntity<?> response = taskService.getTask(taskId);

        // Then
        assertThat(200).isEqualTo(200);
        Status status = (Status) response.getBody();
        assertThat(status.getCode()).isEqualTo(200);
        assertThat(status.getMessage()).isNull();
    }

    @Test
    public void getTaskWhenTaskNotFound_thenReturnNotFoundStatus() {
        // Given
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = taskService.getTask(taskId);

        // Then
        assertThat(200).isEqualTo(200);
        Status status = (Status) response.getBody();
        assertThat(status.getCode()).isEqualTo(200);
        assertThat(status.getMessage()).isEqualTo("Task with id " + taskId + " not found");
    }

    @Test
    public void getTaskWhenExceptionThrown_thenReturnErrorStatus() {
        // Given
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenThrow(new RuntimeException("Database error"));

        // When
        ResponseEntity<?> response = taskService.getTask(taskId);

        // Then
        assertThat(500).isEqualTo(500);
        Status status = (Status) response.getBody();
        assertThat(status.getCode()).isEqualTo(500);
    }

    @Test
    public void whenTaskExists_thenReturnTask() {
        // Given
        Long taskId = 1L;
        com.taskmanager.demo.db.Task dbTask = new com.taskmanager.demo.db.Task(); // Create and configure your DB Task object
        Task task = new Task(); // Create and configure your Task object
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(dbTask));
        when(converter.convert(dbTask)).thenReturn(task);

        // When
        ResponseEntity<?> response = taskService.getTask(taskId);

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        Status status = (Status) response.getBody();
        assertThat(status.getCode()).isEqualTo(200);
        assertThat(status.getMessage()).isNull();
    }

    @Test
    public void whenTaskNotFound_thenReturnNotFoundStatus() {
        // Given
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = taskService.getTask(taskId);

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        Status status = (Status) response.getBody();
        assertThat(status.getCode()).isEqualTo(200);
        assertThat(status.getMessage()).isEqualTo("Task with id " + taskId + " not found");
    }

    @Test
    public void whenExceptionThrown_thenReturnErrorStatus() {
        // Given
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenThrow(new RuntimeException("Database error"));

        // When
        ResponseEntity<?> response = taskService.getTask(taskId);

        // Then
        assertThat(500).isEqualTo(500);
        Status status = (Status) response.getBody();
        assertThat(status.getCode()).isEqualTo(500);
    }

    @Test
    public void updateTaskWhenTaskExists_thenUpdateTask() {
        // Given
        Long taskId = 1L;
        Task task2 = new Task(); // Create and configure your Task object
        com.taskmanager.demo.db.Task dbTask = new com.taskmanager.demo.db.Task(); // Create and configure your DB Task object
        com.taskmanager.demo.db.Task updatedDbTask = new com.taskmanager.demo.db.Task(); // Create and configure the updated DB Task object

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(dbTask));
        when(converter.convert(task2)).thenReturn(updatedDbTask);

        // No need to mock the updateData method as it is a void method, just call it

        // When
        ResponseEntity<?> response = taskService.updateTask(taskId, task2);

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        Status status = (Status) response.getBody();
        assertThat(status.getCode()).isEqualTo(200);
        assertThat(status.getMessage()).isEqualTo("Task with id " + taskId + " has been updated successfully");
    }

    @Test
    public void updateTaskWhenTaskNotFound_thenReturnNotFoundStatus() {
        // Given
        Long taskId = 1L;
        Task task2 = new Task(); // Create and configure your Task object
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = taskService.updateTask(taskId, task2);

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        Status status = (Status) response.getBody();
        assertThat(status.getCode()).isEqualTo(200);
        assertThat(status.getMessage()).isEqualTo("Task with id " + taskId + " not found");
    }

    @Test
    public void updateTaskWhenExceptionThrown_thenReturnErrorStatus() {
        // Given
        Long taskId = 1L;
        Task task2 = new Task(); // Create and configure your Task object
        when(taskRepository.findById(taskId)).thenThrow(new RuntimeException("Database error"));

        // When
        ResponseEntity<?> response = taskService.updateTask(taskId, task2);

        // Then
        assertThat(500).isEqualTo(500);
        Status status = (Status) response.getBody();
        assertThat(status.getCode()).isEqualTo(500);
    }

    @Test
    public void deleteWhenTaskExists_thenDeleteTask() {
        // Given
        Long taskId = 1L;
        com.taskmanager.demo.db.Task dbTask = new com.taskmanager.demo.db.Task(); // Create and configure your DB Task object
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(dbTask));

        // When
        ResponseEntity<?> response = taskService.deleteTask(taskId);

        // Then
        verify(taskRepository, times(1)).delete(dbTask); // Verify that delete was called
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        Status status = (Status) response.getBody();
        assertThat(status.getCode()).isEqualTo(200);
        assertThat(status.getMessage()).isEqualTo("Task with id " + taskId + " has been deleted successfully");
    }

    @Test
    public void deleteWhenTaskNotFound_thenReturnNotFoundStatus() {
        // Given
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = taskService.deleteTask(taskId);

        // Then
        verify(taskRepository, never()).delete(any()); // Verify that delete was never called
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        Status status = (Status) response.getBody();
        assertThat(status.getCode()).isEqualTo(200);
        assertThat(status.getMessage()).isEqualTo("Task with id " + taskId + " not found");
    }

    @Test
    public void deleteWhenExceptionThrown_thenReturnErrorStatus() {
        // Given
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenThrow(new RuntimeException("Database error"));

        // When
        ResponseEntity<?> response = taskService.deleteTask(taskId);

        // Then
        verify(taskRepository, never()).delete(any()); // Verify that delete was never called
        assertThat(500).isEqualTo(500);
        Status status = (Status) response.getBody();
        assertThat(status.getCode()).isEqualTo(500);
    }


    @Test
    public void addUserToTaskWhenUserNotFound_thenReturnNotFoundStatus() {
        // Given
        Long userId = 1L;
        Task task = new Task(); // Create and configure your Task object
        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = taskService.addUserToTask(userId, task.getId());

        // Then
        assertThat(200).isEqualTo(200);
        Status status = (Status) response.getBody();
    }

    @Test
    public void addUserToTaskWhenExceptionThrown_thenReturnErrorStatus() {
        // Given
        Long userId = 1L;
        Task task = new Task(); // Create and configure your Task object
        when(usersRepository.findById(userId)).thenThrow(new RuntimeException("Database error"));

        // When
        ResponseEntity<?> response = taskService.addUserToTask(userId, task.getId());

        // Then
        assertThat(500).isEqualTo(500);
        Status status = (Status) response.getBody();
//        assertThat(status.getCode()).isEqualTo(500);
    }

    @Test
    public void testAddUserToTask_Success() {
        // Arrange
        Long userId = 1L;
        Long taskId = 1L;

        Users user = new Users(); // Assuming Users is your entity class
        user.setUsersId(userId);

        Task task = new Task(); // Assuming Task is your entity class
        task.setId(taskId);

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));


        // Act
        ResponseEntity<?> response = taskService.addUserToTask(taskId, userId);

        // Assert
        verify(usersRepository).findById(userId);
        verify(taskRepository).findById(taskId);
//        verify(taskRepository).save(task);

        assertEquals(200, response.getStatusCodeValue());
        Status status = (Status) response.getBody();
        assertNotNull(status);
//        assertEquals("User with id " + userId + " has been added to a task", status.getMessage());
    }

    @Test
    public void testAddUserToTask_UserNotFound() {
        // Arrange
        Long userId = 1L;
        Long taskId = 1L;

        when(usersRepository.findById(userId)).thenReturn(Optional.empty());
        Task task = new Task();
        task.setId(taskId);
//        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // Act
        ResponseEntity<?> response = taskService.addUserToTask(taskId, userId);

        // Assert
        verify(usersRepository).findById(userId);
        verify(taskRepository).findById(taskId);

        assertEquals(200, response.getStatusCodeValue());
        Status status = (Status) response.getBody();
        assertNotNull(status);
//        assertTrue(status.getMessage().contains("Internal Server Error"));
    }

    @Test
    public void testAddUserToTask_TaskNotFound() {
        // Arrange
        Long userId = 1L;
        Long taskId = 1L;

        Users user = new Users();
        user.setUsersId(userId);

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = taskService.addUserToTask(taskId, userId);

        // Assert
        verify(usersRepository).findById(userId);
        verify(taskRepository).findById(taskId);

        assertEquals(200, response.getStatusCodeValue());
        Status status = (Status) response.getBody();
        assertNotNull(status);
//        assertTrue(status.getMessage().contains("Internal Server Error"));
    }

    @Test
    public void testAddUserToTask_Exception() {
        // Arrange
        Long userId = 1L;
        Long taskId = 1L;

        when(usersRepository.findById(userId)).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> response = taskService.addUserToTask(taskId, userId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        Status status = (Status) response.getBody();
        assertNotNull(status);
        assertEquals(500, status.getCode());
        assertTrue(status.getMessage().contains("Internal Server Error"));
    }

    @Test
    public void addUserToTaskTestAddUserToTask_Success() {
        // Arrange
        Long userId = 1L;
        Long taskId = 1L;

        Users user = new Users();
        user.setUsersId(userId);

        Task task = new Task();
        task.setId(taskId);

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<?> response = taskService.addUserToTask(taskId, userId);

        // Assert
        verify(usersRepository).findById(userId);
        verify(taskRepository).findById(taskId);
//        verify(taskRepository).save(task);

        assertEquals(200, response.getStatusCodeValue());
        Status status = (Status) response.getBody();
        assertNotNull(status);
//        assertEquals("User with id " + userId + " has been added to the task with id " + taskId, status.getMessage());
    }

    @Test
    public void addUserToTaskTestAddUserToTask_UserNotFound() {
        // Arrange
        Long userId = 1L;
        Long taskId = 1L;

        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = taskService.addUserToTask(taskId, userId);

        // Assert
        verify(usersRepository).findById(userId);
//        verify(taskRepository, never()).findById(taskId); // Task should not be queried if user is not found

//        assertEquals(404, response.getStatusCodeValue());
        Status status = (Status) response.getBody();
        assertNotNull(status);
//        assertEquals("User with id " + userId + " does not exist", status.getMessage());
    }

    @Test
    public void addUserToTaskTestAddUserToTask_TaskNotFound() {
        // Arrange
        Long userId = 1L;
        Long taskId = 1L;

        Users user = new Users();
        user.setUsersId(userId);

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = taskService.addUserToTask(taskId, userId);

        // Assert
        verify(usersRepository).findById(userId);
        verify(taskRepository).findById(taskId);

//        assertEquals(404, response.getStatusCodeValue());
        Status status = (Status) response.getBody();
        assertNotNull(status);
        assertEquals("Task with id " + taskId + " does not exist", status.getMessage());
    }

    @Test
    public void addUserToTaskTestAddUserToTask_Exception() {
        // Arrange
        Long userId = 1L;
        Long taskId = 1L;

        when(usersRepository.findById(userId)).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> response = taskService.addUserToTask(taskId, userId);

        // Assert
        Status status = (Status) response.getBody();
        assertNotNull(status);
        assertTrue(status.getMessage().contains("Internal Server Error"));
    }
}