package my.maven.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import my.maven.project.entity.Users;

public interface TemplateRepository extends JpaRepository<Users, Long> {

}
