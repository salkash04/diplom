package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Module;
import ru.kata.spring.boot_security.demo.repository.ModuleRepository;

import java.util.List;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    @Autowired
    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> getAllModules() {
        return moduleRepository.findAll(); // Получение всех модулей
    }

    public Module saveModule(Module module) {
        return moduleRepository.save(module); // Сохранение модуля в базе данных
    }
}
