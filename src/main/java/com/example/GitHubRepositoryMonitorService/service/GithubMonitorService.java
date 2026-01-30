package com.example.GitHubRepositoryMonitorService.service;
import com.example.GitHubRepositoryMonitorService.client.GithubClient;
import com.example.GitHubRepositoryMonitorService.dtos.GithubResponseDto;
import com.example.GitHubRepositoryMonitorService.dtos.RequestDto;
import com.example.GitHubRepositoryMonitorService.dtos.ResponseDto;
import com.example.GitHubRepositoryMonitorService.entity.GithubMonitor;
import com.example.GitHubRepositoryMonitorService.enums.status;
import com.example.GitHubRepositoryMonitorService.exceptions.GithubAccountFoundException;
import com.example.GitHubRepositoryMonitorService.exceptions.IdNotFoundException;
import com.example.GitHubRepositoryMonitorService.exceptions.LanguageAndStatusNotFoundException;
import com.example.GitHubRepositoryMonitorService.mapper.GithubMonitorMapper;
import com.example.GitHubRepositoryMonitorService.repository.GithubMonitorRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class GithubMonitorService {
    private final GithubMonitorRepository repository;
    private final GithubMonitorMapper mapper;
    private final GithubClient githubClient;


    public GithubMonitorService(GithubMonitorRepository repositoryMonitorRepository, GithubMonitorMapper mapper, GithubClient githubClient) {
        this.repository = repositoryMonitorRepository;
        this.mapper = mapper;
        this.githubClient = githubClient;
    }

    public ResponseDto addRepository(RequestDto request) {
        GithubResponseDto gitData ;
        try {
            gitData = githubClient.getRepositoryDetails(request.getOwner(), request.getRepoName()).block();
            if (gitData == null) {
                throw new GithubAccountFoundException("Github bulunamadı : " + request.getRepoName());
            }

        } catch (Exception e) {
            throw new GithubAccountFoundException("Bulunamadı : " + e.getMessage() + HttpStatus.NOT_FOUND);
        }
        GithubMonitor entity = GithubMonitor.builder()
                .owner(request.getOwner())
                .repoName(request.getRepoName())
                .stars(gitData.getStars())
                .forks(gitData.getForks())
                .openIssues(gitData.getOpenIssues())
                .language(gitData.getLanguage())
                .status(status.ACTIVE)
                .createdAt(LocalDateTime.now())
                .lastSyncedAt(LocalDateTime.now())
                .build();
        GithubMonitor saved = repository.save(entity);
        return mapper.toDto(saved);
    }

  public Page<ResponseDto> getAllRepositories(Pageable pageable){

        Page<GithubMonitor> repositoryMonitorPage = repository.findAll(pageable);

      return repositoryMonitorPage.map(mapper::toDto);  }
    public void deleteRepository(UUID id){
        if (id == null || !repository.existsById(id)) {
            throw new IdNotFoundException("Silinecek id yok");
        }
        repository.deleteById(id);
    }

    public ResponseDto  getOneRepository(UUID id) {
        GithubMonitor repositoryMonitor = repository.findById(id).orElseThrow(() -> new IdNotFoundException("Id bulunamadı: " + id));
            return mapper.toDto(repositoryMonitor);
    }
        public ResponseDto updateRepository(UUID id ,RequestDto request){
            GithubMonitor repositoryMonitor = repository.findById(id)
                    .orElseThrow(() -> new IdNotFoundException("Id bulunamadı: " + id));
            repositoryMonitor.setOwner(request.getOwner());
            repositoryMonitor.setRepoName(request.getRepoName());

            GithubResponseDto gitData;
            try {
                gitData = githubClient.getRepositoryDetails(
                        repositoryMonitor.getOwner(),
                        repositoryMonitor.getRepoName()
                ).block();

                if (gitData == null) {
                    throw new GithubAccountFoundException("GitHub'da kayıt bulunamadı");
                }
                repositoryMonitor.setStars(gitData.getStars());
                repositoryMonitor.setForks(gitData.getForks());
                repositoryMonitor.setOpenIssues(gitData.getOpenIssues());
                repositoryMonitor.setLanguage(gitData.getLanguage());
                repositoryMonitor.setStatus(status.ACTIVE);
                repositoryMonitor.setLastSyncedAt(LocalDateTime.now());

            } catch (Exception e) {
                repositoryMonitor.setStatus(status.FAILED);
                repository.save(repositoryMonitor);
                throw new RuntimeException("Güncellemede hata oluştu: " + e.getMessage());
            }
            GithubMonitor updatedEntity = repository.save(repositoryMonitor);
            return mapper.toDto(updatedEntity);
        }
        public Page<ResponseDto> findByLanguageAndStatus(String language, status status, Pageable pageable) {
            Page<GithubMonitor> githubMonitors= null ;
            if (language != null && status != null) {
                githubMonitors = repository.findByLanguageAndStatus(language, status, pageable);
            }
            if(language !=null) {
                githubMonitors = repository.findByLanguage(language,pageable);
            }
            if(status != null ){
                githubMonitors = repository.findByStatus(status,pageable);
                        }
            else{
                throw new LanguageAndStatusNotFoundException("Language ve Status Boş");
            }
            return githubMonitors.map(mapper::toDto);
    }

}



